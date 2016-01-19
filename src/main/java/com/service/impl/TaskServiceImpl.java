package com.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.model.MonitorDealer;
import com.model.dd.OfferFactory;
import com.service.MonitorService;
import com.service.TaskService;
import com.tpadsz.ctc.api.SettingManager;
import com.tpadsz.ctc.api.ShelfManager;
import com.tpadsz.ctc.api.TaskManager;
import com.tpadsz.ctc.vo.Task;
import com.tpadsz.ctc.vo.setting.Setting;
import com.tpadsz.exception.ApplicationNotCorrectException;
import com.tpadsz.exception.NotFoundException;
import com.tpadsz.exception.SystemAlgorithmException;
import com.tpadsz.exception.TimeoutException;
import com.utils.Constants;
import com.utils.LoggerUtils;
import com.utils.StringUtil;

@Service("taskService")
public class TaskServiceImpl implements TaskService {

	public static final String MEMCACHED_TASK_REPEATED = "ctc_task_validator_repeated_%s_%s_";
	public static final String REDIS_TASK_REPEATED = "ctc_task_validator_repeated_%s_%s";
	public static final String REDIS_TASK_SATSTE = "ctc_task_state_%s_%s_%s";
	public static final String TASK_KEY = "TaskState-%s-%s-dataMap";
	private Logger logger = LoggerUtils.DAYLOG;
	private Logger system = LoggerUtils.SYSTEM;
	private TaskManager taskManager;
	private SettingManager settingManager;
	private ShelfManager shelfManager;
	private MonitorService monitorService;
	private Long taskGenerateInitialDelay = 0l;
	private Long taskGeneratePeriod = 5 * 60 * 1000l;
	private MemcachedClient memcachedClient;
	private RedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

	@PostConstruct
	@Override
	public void init() {
		generateTasks();
	}
	
	@Override
	/**
	 * Map<tid, Map<did, List<Map<'type':'', 'code':'', 'name':'', 'price':'', 'hintMsg':'', 'hintTimeout':''>>>>
	 */
	public Map<String, Map<String, List<Object>>> getUserTaskDataInfo(String uid) throws SystemAlgorithmException, TimeoutException{
		Map<String, Map<String, List<Object>>> info = Maps.newHashMap();
		List<Task> tasks = getTasks();
		if(CollectionUtils.isNotEmpty(tasks)){
			for(Task task : tasks){
				//String key = String.format(MemcachedObjectType.CACHE_USER_TASK_DATAMAP.getPrefix(), uid, task.getId());
		        String key = String.format(TASK_KEY, uid, task.getId());
//		        String dataInfo = null;
//		        try {
//		            dataInfo = memcachedClient.get(key, Constants.CACHE_MEMCACHED_TIMEOUT);
//		        } catch (java.util.concurrent.TimeoutException | InterruptedException | MemcachedException e) {
//		            throw new TimeoutException("bean:memcachedClient, method:get, key:" + key);
//		        }
		        String dataInfo = (String) redisTemplate.opsForValue().get(key);
				Map<String, List<Object>> taskDataInfo = convertTaskDataInfo(task, dataInfo);
				info.put(task.getId(), taskDataInfo);
			}
		}
		return info;
	}

	public Map<String, List<Object>> convertTaskDataInfo(Task task, String dataInfo) {
		Map<String, List<Object>> taskDataInfo = Maps.newHashMap();
		JSONArray array = JSONArray.parseArray(dataInfo);
		if(array != null && array.size() == 2){
			JSONObject finishedDataInfo = JSONObject.parseObject(array.getString(1));
			if(CollectionUtils.isNotEmpty(task.getData())){
				for(Map data : task.getData()){
					String id = String.valueOf(data.get("id"));
					List<Object> dataInfoBroadcasts = finishedDataInfo.getJSONArray(id);
					if(CollectionUtils.isNotEmpty(dataInfoBroadcasts)){
						List<Object> checkedDataInfoBroadcasts = Lists.newArrayList();
						for (Object obj : dataInfoBroadcasts) {
							if (obj instanceof JSONObject) {
								checkedDataInfoBroadcasts.add(obj);
							} else {
								JSONObject map = new JSONObject();
								map.put("type", String.valueOf(obj));
								map.put("logDate", new Date());
								checkedDataInfoBroadcasts.add(map);
							}
						}
						taskDataInfo.put(id, dataInfoBroadcasts);
					}
				}
			}
		}
		return taskDataInfo;
	}

	@Override
	public void generateTasks() {
		monitorService.monitor(new MonitorDealer() {

			@Override
			public void success(Map<String, Object> data) {
				Setting setting = (Setting) data.get("setting");
				setupTaskExpireTime(setting);
			}

			@Override
			public void stop() {
				// TODO Auto-generated method stub
			}

			@Override
			public void fail(Exception ex) {
				system.error("TaskService Generate Tasks fail in error.", ex);
			}

			@Override
			public Map<String, Object> start() throws Exception {
				Map<String, Object> data = Maps.newHashMap();
				logger.warn("TaskService Generate Tasks start.");
				Setting setting = settingManager.getSetting(OfferFactory.generateOffer(), Constants.APP_ID);
				if (setting == null) {
					throw new RuntimeException("Setting is null. ");
				}
				data.put("setting", setting);
				taskManager.refreshTasks(OfferFactory.generateOffer());
				return data;
			}
		}, taskGenerateInitialDelay, taskGeneratePeriod);
	}

	private void setupTaskExpireTime(Setting setting) {
		Integer hour = null;
		Integer minute = null;
		String refreshTime = setting.getRefreshTime();
		String[] params = refreshTime.split(":");
		hour = Integer.valueOf(params[0]);
		minute = Integer.valueOf(params[1]);
		Calendar calendar = Calendar.getInstance();
		if (calendar.get(Calendar.HOUR_OF_DAY) < hour
				&& (calendar.get(Calendar.MINUTE) < minute)) {
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		}
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		Constants.CTC_TASKS_STARTTIME = calendar.getTime();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		Constants.CTC_TASKS_ENDTIME = calendar.getTime();
		logger.warn("CTC_TASKS_STARTTIME:" + Constants.CTC_TASKS_STARTTIME);
		logger.warn("CTC_TASKS_ENDTIME:" + Constants.CTC_TASKS_ENDTIME);
		logger.warn("TaskService Generate Tasks succeed.");
	}

	@Autowired
	public void setTaskManager(TaskManager taskManager) {
		this.taskManager = taskManager;
	}

	@Autowired
	public void setSettingManager(SettingManager settingManager) {
		this.settingManager = settingManager;
	}

	@Autowired
	public void setMonitorService(MonitorService monitorService) {
		this.monitorService = monitorService;
	}

	public void setTaskGenerateInitialDelay(Long taskGenerateInitialDelay) {
		this.taskGenerateInitialDelay = taskGenerateInitialDelay;
	}

	public void setTaskGeneratePeriod(Long taskGeneratePeriod) {
		this.taskGeneratePeriod = taskGeneratePeriod;
	}

	@Override
	@Cacheable(value = "taskService", key = "'getTasks'")
	public List<Task> getTasks() throws SystemAlgorithmException, TimeoutException {
		List<Task> tasks = null;
		try {
			tasks = shelfManager.getTodayTasks(OfferFactory.generateShelfOffer(Constants.TASK_SHELF_USER));
		} catch (ApplicationNotCorrectException e) {
			throw new SystemAlgorithmException("appid:1 not correct", e);
		} catch(NotFoundException e){
			throw new SystemAlgorithmException("shelfId:1 not found", e);
		}
		return tasks;
	}

	@Autowired
	public void setShelfManager(ShelfManager shelfManager) {
		this.shelfManager = shelfManager;
	}

	@Autowired
	public void setMemcachedClient(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}

	@Override
	public Integer getUserTaskInfo(String uid, String tid) throws TimeoutException {
		Integer repeated = Constants.propertiesLoader.getInteger("task.info.repeated." + tid, null);
		if(repeated != null){
			return repeated;
		}
		String key = String.format(MEMCACHED_TASK_REPEATED, uid, tid);
		int times = 0;
		while(times < 3){
			try {
				Integer cachedRepeatNum = memcachedClient.get(key);
				return cachedRepeatNum;
			} catch (java.util.concurrent.TimeoutException | InterruptedException | MemcachedException e) {
				logger.warn("bean:memcachedClient, method:cas, uid:" + uid, e);;
			}finally{
				times ++;
			}
		}
		throw new com.tpadsz.exception.TimeoutException("bean:memcachedClient, method:cas, times:" + times);
	}
	
	@Override
	public List<String> getUserTaskInfo(String uid, List<String> tids)
			throws TimeoutException {
		 if (CollectionUtils.isEmpty(tids)) {
	            return null;
	        }
//			List<String> memcachedKeys = Lists.newArrayListWithCapacity(tids.size());
	        List<String> redisKeys = Lists.newArrayListWithCapacity(tids.size());
	        for (String tid : tids) {
//				String memcachedKey = String.format(MEMCACHED_TASK_REPEATED, uid, tid);
	            String redisKey = String.format(REDIS_TASK_REPEATED, uid, tid);
//				memcachedKeys.add(memcachedKey);
	            redisKeys.add(redisKey);
	        }
	        List<String> redisValues = redisTemplate.opsForValue().multiGet(redisKeys);
	        return redisValues;
	}
    
	@Override
	public List<Task> getTpadTasks(String uid) throws SystemAlgorithmException,
			TimeoutException {
		List<Task> tasks = null;
		try {
			tasks = shelfManager.getTodayTasks(OfferFactory.generateShelfOffer(Constants.TASK_SHELF_USER));
		} catch (ApplicationNotCorrectException e) {
			throw new SystemAlgorithmException("appid: "
					+ Constants.TASK_SHELF_USER + "not correct", e);
		} catch (NotFoundException e) {
			throw new SystemAlgorithmException("shelfId:"
					+ Constants.TASK_SHELF_USER + " not found", e);
		}
		return filterUserTasks(uid, tasks);
	}
	
	private Map<String, List<Object>> getUserTaskDataInfo(String uid, Task task) throws TimeoutException {
        if (!StringUtils.equalsIgnoreCase(task.getType(), "004")) {
            return null;
        }
        String key = String.format(TASK_KEY, uid, task.getId());
        //String key = String.format(MemcachedObjectType.CACHE_USER_TASK_DATAMAP.getPrefix(), uid, task.getId());
        //String dataInfo = null;
        Map<String, List<Object>> taskDataInfo = new HashMap<String, List<Object>>();
//        try {
//            dataInfo = memcachedClient.get(key, 300L);
//            taskDataInfo = convertTaskDataInfo(task, dataInfo);
//        } catch (java.util.concurrent.TimeoutException | InterruptedException | MemcachedException e) {
//            throw new TimeoutException("bean:memcachedClient, method:get, key:" + key);
//        } 
        String dataInfo = (String) redisTemplate.opsForValue().get(key);
        taskDataInfo = convertTaskDataInfo(task, dataInfo);
        return taskDataInfo;
    }

	private List<Task> filterUserTasks(String uid, List<Task> tasks)
			throws TimeoutException {
		if (CollectionUtils.isEmpty(tasks)) {
			return tasks;
		}
		List<String> taskIds = Lists.newArrayListWithCapacity(tasks.size());
		Map<String, Map<String, List<Object>>> taskDataInfos = Maps
				.newHashMap();
		for (Task task : tasks) {
			taskIds.add(task.getId());
			if (StringUtils.equalsIgnoreCase(task.getType(), "004")) {
				taskDataInfos.put(task.getId(), getUserTaskDataInfo(uid, task));
			}
		}
		List<String> taskRepeatedTimes = getUserTaskInfo(uid, taskIds);
		List<Task> filteredTasks = Lists.newArrayList();
		for (int index = 0; index < tasks.size(); index++) {
			Task task = tasks.get(index);
			try {
				if (task.getRepeated() > 0) {
					int taskRepeatedTime = taskRepeatedTimes.get(index) == null ? 0
							: Integer.valueOf(taskRepeatedTimes.get(index));
					if (taskRepeatedTime >= task.getRepeated()) {
						continue;
					}
				}
				if (StringUtils.equalsIgnoreCase(task.getType(), "004")) {
					Map<String, List<Object>> taskDataInfo = taskDataInfos
							.get(task.getId());
					if (MapUtils.isNotEmpty(taskDataInfo)) {
						Task dataInfoFilteredTask = filterTaskDataInfo(task,
								taskDataInfo);
						if (dataInfoFilteredTask != null) {
							filteredTasks.add(dataInfoFilteredTask);
							continue;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(
						"bean:TaskServiceImpl, method:filterUserTasks, taskId:"
								+ task.getId(), e);
				continue;
			}
			filteredTasks.add(task);
		}
		return filteredTasks;

	}
	
	private Task filterTaskDataInfo(Task task, Map<String, List<Object>> taskDataInfo) {
        List<Map> datas = Lists.newArrayListWithCapacity(task.getData().size());
        for (Map data : task.getData()) {
            String did = String.valueOf(data.get("id"));
            JSONObject broadcasts = JSONObject.parseObject(String.valueOf(data.get("broadcast")));
            List<Object> finishedStatus = taskDataInfo.get(did);
            if(CollectionUtils.isNotEmpty(finishedStatus)){
                Set<String> finishedBroadcastTypes = getFinishedBroadcast(finishedStatus);
                if(CollectionUtils.isNotEmpty(finishedBroadcastTypes)){
                    for(String broadcastType : finishedBroadcastTypes){
                        broadcasts.remove(broadcastType);
                    }
                    data.put("broadcast", broadcasts.toJSONString());
                }
            }
            datas.add(data);
        }
        if(CollectionUtils.isEmpty(datas)){
            return null;
        }
        task.setData(datas);
        return task;
    }
	
	private Set<String> getFinishedBroadcast(List<Object> allBroadcasts) {
		
        Set<String> finishedBroadcastTypes = Sets.newHashSetWithExpectedSize(allBroadcasts.size());
		for (Object object : allBroadcasts) {
			try {
				String broadcastType = ((JSONObject) object).getString("type");
				if (broadcastType != null) {
					finishedBroadcastTypes.add(String.valueOf(broadcastType));
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(
						"bean:TaskServiceImpl, method:getFinishedBroadcast", e);
				continue;
			}
		}
        return finishedBroadcastTypes;
    }

	/**
	 * 获取当日可做深度任务
	 */
	@Override
	public List<Map> getDeepTasks(String uid) throws SystemAlgorithmException,
			TimeoutException {
		List<Task> tasks = null;
		try {
			// 获取任务列表
			tasks = shelfManager.getTodayTasks(OfferFactory.generateShelfOffer(Constants.TASK_SHELF_USER));
		} catch (ApplicationNotCorrectException e) {
			throw new SystemAlgorithmException("appid: "
					+ Constants.TASK_SHELF_USER + "not correct", e);
		} catch (NotFoundException e) {
			throw new SystemAlgorithmException("shelfId:"
					+ Constants.TASK_SHELF_USER + " not found", e);
		}
		List<Map> datas = new ArrayList<Map>();
		for (Task task : tasks) {
			// 获取cpa任务的各项信息
			if (StringUtils.equalsIgnoreCase(task.getType(), "004")) {
				 datas = filterDeepTaskInfo(uid,task, getUserTaskDataInfo(uid, task));
			}
		}
		
		return datas;
	}
	
	/**
	 * 过滤当日可做的深度任务
	 * @param task
	 * @param taskDataInfo
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Map> filterDeepTaskInfo(String uid, Task task, Map<String, List<Object>> taskDataInfo) {
        List<Map> datas = Lists.newArrayListWithCapacity(task.getData().size());
		for (Map data : task.getData()) {
			String did = String.valueOf(data.get("id"));
			String key = String.format(REDIS_TASK_SATSTE, uid, task.getId(),did);
			String taskState = (String) redisTemplate.opsForValue().get(key);
			String date = StringUtil.dateToStringWithNumber(new Date());
			if (taskState != null && date.equals(taskState.substring(0, 10).replaceAll("-", ""))) {
				continue;
			}
			JSONObject broadcast = JSONObject.parseObject(String.valueOf(data
					.get("broadcast")));
			// 新接口特殊对应，新客户端没有open_2
			broadcast.remove("open_2");
			List<Object> finishedStatus = taskDataInfo.get(did);
			if (CollectionUtils.isNotEmpty(finishedStatus)) {
				List<String> unused = unusedDeepTaskType(finishedStatus);
				for (String item : unused) {
					broadcast.remove(item);
				}
				if (broadcast.isEmpty()) {
					continue;
				}
				data.put("broadcast", broadcast.toJSONString());
				datas.add(data);
			}
		}
		if (CollectionUtils.isEmpty(datas)) {
			return null;
		}
        return datas;
    }

	/**
	 * 获取当日不可做的深度任务broadcastType
	 * @param finishedStatus
	 * @return
	 */
	private List<String> unusedDeepTaskType (List<Object> finishedStatus) {
		List<String> broadCastTypes = new ArrayList<String>();
		Collections.addAll(broadCastTypes, Constants.BROADCASTTYPE.split(",")); 
		List<String> unusedBroadCastTypes = new ArrayList<String>();
		Collections.addAll(unusedBroadCastTypes, Constants.BROADCASTTYPE.split(",")); 
		Set<String> finishedBroadcastTypes = getFinishedBroadcast(finishedStatus);
		broadCastTypes.removeAll(finishedBroadcastTypes);
		String todayType = broadCastTypes.get(0);
		unusedBroadCastTypes.remove(todayType);
		return unusedBroadCastTypes;
	}
}
