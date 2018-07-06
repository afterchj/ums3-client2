package com.web.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.model.dd.OfferFactory;
import com.model.dd.ResultDict;
import com.service.ClientFileService;
import com.service.TaskService;
import com.tpadsz.cic.coin.exception.CoinsNotEnoughException;
import com.tpadsz.ctc.api.UserManager;
import com.tpadsz.ctc.exception.CoinsCheckCodeNotAllowedException;
import com.tpadsz.ctc.exception.TaskAppAlreadyCompletedException;
import com.tpadsz.ctc.exception.TaskAppAlreadyRemovedException;
import com.tpadsz.ctc.exception.TaskAppTypeAlreadyRemovedException;
import com.tpadsz.ctc.exception.TaskDataBlankException;
import com.tpadsz.ctc.exception.TaskDataSerialBroadcastNotNowException;
import com.tpadsz.ctc.exception.TaskDataSerialBroadcastNotParsedException;
import com.tpadsz.ctc.exception.TaskDealerException;
import com.tpadsz.ctc.exception.TaskEveryDayLimitException;
import com.tpadsz.ctc.exception.TaskEveryHourLimitException;
import com.tpadsz.ctc.exception.TaskNotExistException;
import com.tpadsz.ctc.exception.TaskNotStartedYetException;
import com.tpadsz.ctc.exception.TaskOutOfDateException;
import com.tpadsz.ctc.exception.TaskRepeatException;
import com.tpadsz.ctc.exception.TaskValidatorException;
import com.tpadsz.ctc.vo.Present;
import com.tpadsz.ctc.vo.Task;
import com.tpadsz.ctc.vo.UserCommitOffer;
import com.tpadsz.exception.ApplicationNotCorrectException;
import com.tpadsz.exception.CheckNotAllowedException;
import com.tpadsz.exception.ParamBlankException;
import com.tpadsz.exception.SystemAlgorithmException;
import com.tpadsz.exception.TimeoutException;
import com.utils.Constants;
import com.web.vo.ClientFileVo;
import com.web.vo.TaskVo;

@Controller("taskController")
@RequestMapping("/task")
public class TaskController extends BaseDecodedController {

    private UserManager userManager;
    private TaskService taskService;
    private ClientFileService clientFileService;

    private Logger logger = Logger.getLogger(TaskController.class);

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String list(@ModelAttribute("decodedParams") JSONObject params, ModelMap model) {
        return view(params, model);
    }

    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public String view(@ModelAttribute("decodedParams") JSONObject params, ModelMap model) {
        try {
            String uid = params.getString("uid");
            List<Task> tasks = taskService.getTpadTasks(uid);
            model.put("tasks", convert(tasks));
            ClientFileVo client = getClient();
            model.put("result", ResultDict.SUCCESS.getCode());
            model.put("startTime", Constants.CTC_TASKS_STARTTIME);
            model.put("endTime", Constants.CTC_TASKS_ENDTIME);
            model.put("client", client);
        } catch (Exception e) {
//			system.error("bean:taskController, method:list", e);
            model.put("result", ResultDict.SYSTEM_ERROR.getCode());
        }
        return null;
    }

    @RequestMapping(value = "/data/info", method = RequestMethod.POST)
    public String dataInfo(@ModelAttribute("decodedParams") JSONObject params, ModelMap model) {
        String uid = params.getString("uid");
        try {
            if (StringUtils.isNotBlank(uid)) {
                if (Constants.propertiesLoader.getBoolean("task.data.info.oldversion.fake", false)) {
                    Map<String, Map<String, List<String>>> oldVer357DataMap = simpleOldVer357Compatibility();
                    Map<String, Map<String, List<String>>> dataInfo = oldVer357Compatibility(taskService.getUserTaskDataInfo(uid));
                    if (MapUtils.isNotEmpty(dataInfo)) {
                        for (String tid : dataInfo.keySet()) {
                            Map<String, List<String>> taskDataInfo = dataInfo.get(tid);
                            if (StringUtils.equalsIgnoreCase("f4ca48cf5df2477eb7fe67780fdcd1ac", tid)) {
                                if (MapUtils.isNotEmpty(taskDataInfo)) {
                                    oldVer357DataMap.get(tid).putAll(taskDataInfo);
                                }
                            } else {
                                oldVer357DataMap.put(tid, taskDataInfo);
                            }
                        }
                    }
                    model.put("dataInfo", oldVer357DataMap);
                } else {
                    Map<String, Map<String, List<Object>>> dataInfo = taskService.getUserTaskDataInfo(uid);
                    model.put("dataInfo", oldVer357Compatibility(dataInfo));
                }
                model.put("result", ResultDict.SUCCESS.getCode());
            }
        } catch (TimeoutException e) {
            model.put("result", ResultDict.TIMEOUT.getCode());
        } catch (Exception e) {
            system.error("bean:taskService, method:getUserTaskDataInfo, uid:" + uid, e);
            model.put("result", ResultDict.SYSTEM_ERROR.getCode());
        }
        return null;
    }

    private Map<String, Map<String, List<String>>> simpleOldVer357Compatibility() {
        Map<String, Map<String, List<String>>> oldVerDataInfo = Maps.newLinkedHashMap();
        Map<String, List<String>> dataInfo = Maps.newLinkedHashMap();
        dataInfo.put("snapshot-cpa-1", Lists.newArrayList("inst, open_2"));
        dataInfo.put("snapshot-cpa-2", Lists.newArrayList("inst, open_2"));
        oldVerDataInfo.put("f4ca48cf5df2477eb7fe67780fdcd1ac", dataInfo);
        return oldVerDataInfo;
    }

    private Map<String, Map<String, List<String>>> oldVer357Compatibility(Map<String, Map<String, List<Object>>> dataInfo) {
        Map<String, Map<String, List<String>>> oldVerDataInfo = Maps.newHashMap();
        if (MapUtils.isNotEmpty(dataInfo)) {
            for (String tid : dataInfo.keySet()) {
                Map<String, List<Object>> taskDataInfo = dataInfo.get(tid);
                Map<String, List<String>> oldVerTaskDataInfo = Maps.newLinkedHashMap();
                if (MapUtils.isNotEmpty(taskDataInfo)) {
                    for (String did : taskDataInfo.keySet()) {
                        List<Object> taskDataBroadcasts = taskDataInfo.get(did);
                        List<String> oldVerTaskDataBroadcasts = Lists.newArrayList();
                        for (Object obj : taskDataBroadcasts) {
                            oldVerTaskDataBroadcasts.add("inst");
                        }
                        oldVerTaskDataInfo.put(did, oldVerTaskDataBroadcasts);
                    }
                }
                oldVerDataInfo.put(tid, oldVerTaskDataInfo);
            }
        }
        return oldVerDataInfo;
    }

    @RequestMapping(value = "/data/search", method = RequestMethod.POST)
    public String searchDataInfo(@ModelAttribute("decodedParams") JSONObject params, ModelMap model) {
        String uid = params.getString("uid");
        String tid = params.getString("tid");
        String did = params.getString("did");
        try {
            if (StringUtils.isNotBlank(uid)) {
                Map<String, Map<String, List<Object>>> dataInfo = taskService.getUserTaskDataInfo(uid);
                model.put("dataInfo", convertDataInfo(dataInfo, tid, did));
                model.put("result", ResultDict.SUCCESS.getCode());
            }
            Integer repeated = taskService.getUserTaskInfo(uid, tid);
            model.put("leftChances", repeated);
        } catch (TimeoutException e) {
            model.put("result", ResultDict.TIMEOUT.getCode());
        } catch (Exception e) {
            system.error("bean:taskService, method:searchDataInfo, uid:" + uid, e);
            model.put("result", ResultDict.SYSTEM_ERROR.getCode());
        }
        return null;
    }

    @RequestMapping(value = "/data/deepTasks", method = RequestMethod.POST)
    public String getDeepTasks(@ModelAttribute("decodedParams") JSONObject params, ModelMap model) {
        try {
            String uid = params.getString("uid");
            String tid = params.getString("tid");
            List<Map> data = taskService.getDeepTasks(uid);
            model.put("data", data);
            ClientFileVo client = getClient();
            model.put("result", ResultDict.SUCCESS.getCode());
            model.put("startTime", Constants.CTC_TASKS_STARTTIME);
            model.put("endTime", Constants.CTC_TASKS_ENDTIME);
            model.put("client", client);
        } catch (Exception e) {
            system.error("bean:taskController, method:getDeepTasks", e);
            model.put("result", ResultDict.SYSTEM_ERROR.getCode());
        }
        return null;
    }

    private Object convertDataInfo(
            Map<String, Map<String, List<Object>>> taskDataInfo, String tid,
            String did) {
        if (MapUtils.isNotEmpty(taskDataInfo)) {
            Map<String, List<Object>> dataInfo = taskDataInfo.get(tid);
            if (MapUtils.isNotEmpty(dataInfo) && StringUtils.isNotBlank(did)) {
                Map<String, List<Object>> tmpDataInfo = Maps.newHashMap();
                tmpDataInfo.put(did, dataInfo.get(did));
                return tmpDataInfo;
            } else {
                return dataInfo;
            }
        } else {
            return null;
        }
    }

    private ClientFileVo getClient() {
        String newestVersion = clientFileService.getNewestVersionCode();
        if (StringUtils.isBlank(newestVersion)) {
//            logger.error("bean:clientFileService, method:getNewestVersionCode, msg:cannot find newestVersion.");
            return null;
        }
        ClientFileVo client = clientFileService.getClientByVersion(newestVersion);
        return client;
    }

    private List<TaskVo> convert(List<Task> tasks) {
        if (CollectionUtils.isEmpty(tasks)) {
            return null;
        }
        List<TaskVo> vos = Lists.newArrayList();
        for (Task task : tasks) {
            vos.add(TaskVo.convert(task));
        }
        return vos;
    }

    public static void main(String[] args) {

    }


    /**
     * @param params
     * @param model
     * @return 返回值说明:
     * 000:成功
     * 200:系统错误.
     * 500:任务不存在 包括CPA任务中具体的App不存在,App 的broadcast不存在, 任务本身不存在等问题.
     * 501:任务重复提交,包括CPA任务中具体的App重复提交或者任务本身重复提交.
     * 201:系统处理超时.
     * 505:深度任务提交失败. 原因为提交的指定天数的深度任务不正确.
     * 401:积分不够
     * 302:参数解析错误. 深度任务broadcast字段解析不出来等参数问题
     * 301:指定需要提交的参数为空:
     * 503:一些还未明确的任务提交校验失败问题.
     */
    @RequestMapping(value = "/commit", method = RequestMethod.POST)
    public String commit(@ModelAttribute("decodedParams") JSONObject params, ModelMap model) {
        try {
            String uid = params.getString("uid");
            String check = params.getString("check");
            String tid = params.getString("tid");
            String token = params.getString("token");
            UserCommitOffer offer = OfferFactory.generateUserCommitOffer(tid, uid, token, check);
            try {
                JSONObject data = params.getObject("data", JSONObject.class);
                if (MapUtils.isNotEmpty(data)) {
                    offer.putValue("data", data);
                    if (StringUtils.isNotBlank(data.getString("profitInviter"))) {
                        offer.putValue("profitInviter", data.getString("profitInviter"));
                    }
                }
            } catch (Exception e) {
                logger.error("prepare data of Params error, params:" + params.toJSONString(), e);
            }
            Present present = userManager.submitTask(offer);
            model.put("result", ResultDict.SUCCESS.getCode());
            if (present != null) {
                model.put("gain", present.getGain());
                model.put("leftChances", present.getLeftChances());
            }
        } catch (CheckNotAllowedException | CoinsCheckCodeNotAllowedException e) {
//			临时处理
            model.put("result", ResultDict.TASK_REPEATED.getCode());
        } catch (SystemAlgorithmException | ApplicationNotCorrectException | TaskDealerException e) {
            system.error("bean:taskController, method:commit", e);
            model.put("result", ResultDict.SYSTEM_ERROR.getCode());
        } catch (TaskDataBlankException | TaskNotExistException | TaskAppAlreadyRemovedException | TaskAppTypeAlreadyRemovedException | TaskNotStartedYetException | TaskOutOfDateException e) {
            model.put("result", ResultDict.TASK_NOT_EXIST.getCode());
        } catch (TaskRepeatException | TaskAppAlreadyCompletedException e) {
            model.put("result", ResultDict.TASK_REPEATED.getCode());
        } catch (TimeoutException e) {
            model.put("result", ResultDict.TIMEOUT.getCode());
        } catch (TaskDataSerialBroadcastNotNowException e) {
            model.put("result", ResultDict.TASK_DATA_SERIAL_BROADCAST_NOT_NOW.getCode());
        } catch (CoinsNotEnoughException e) {
            model.put("result", ResultDict.COINS_NOT_ENOUGH.getCode());
        } catch (TaskDataSerialBroadcastNotParsedException e) {
            model.put("result", ResultDict.PARAMS_NOT_PARSED.getCode());
        } catch (ParamBlankException e) {
            model.put("result", ResultDict.PARAMS_BLANK.getCode());
        } catch (TaskEveryHourLimitException e) {
            model.put("msg", e.getMessage());
            model.put("result", ResultDict.TASK_EVERYHOUR_REPEATED.getCode());
        } catch (TaskEveryDayLimitException e) {
            model.put("msg", e.getMessage());
            model.put("result", ResultDict.TASK_EVERYDAY_REPEATED.getCode());
        } catch (TaskValidatorException e) {
            model.put("msg", e.getMessage());
            model.put("result", ResultDict.TASK_VALIDATOR_ERROR.getCode());
        }
        return null;
    }

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Autowired
    public void setClientFileService(ClientFileService clientFileService) {
        this.clientFileService = clientFileService;
    }


}
