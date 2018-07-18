/*
package com.service.impl;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tpadsz.exception.TimeoutException;

public class TaskServiceImplTest {
	
	public static TaskServiceImpl taskService = new TaskServiceImpl();
	
	private MemcachedClient memcachedClient = mock(MemcachedClient.class);
	
	@Test
	public void testConvertTaskDataInfo() {
		Map<String, List<Object>> taskDataInfo = Maps.newHashMap();
		String dataInfo = "[2913701,'{\"402848f94bee1d56014bfe298144000f\":[{\"ext\":\"inst\",\"gain\":150,\"logDate\":\"2015-04-03 16:02:58\"}]}']";
		JSONArray array = JSONArray.parseArray(dataInfo);
		if(array != null && array.size() == 2){
			JSONObject finishedDataInfo = JSONObject.parseObject(array.getString(1));
			String id = String.valueOf("402848f94bee1d56014bfe298144000f");
			List<Object> dataInfoBroadcasts = finishedDataInfo.getJSONArray(id);
			if(CollectionUtils.isNotEmpty(dataInfoBroadcasts)){
				List<Object> checkedDataInfoBroadcasts = Lists.newArrayList();
				for(Object obj : dataInfoBroadcasts){
					if(obj instanceof JSONObject){
						checkedDataInfoBroadcasts.add(obj);
					}else {
						JSONObject map = new JSONObject();
						map.put("type", String.valueOf(obj));
						map.put("logDate", new Date());
						checkedDataInfoBroadcasts.add(map);
					}
				}
				taskDataInfo.put(id, dataInfoBroadcasts);
			}
		}
		System.out.println(JSONObject.toJSONString(taskDataInfo));
	}
	
	@Test
	public void testGetUserTaskInfo() throws TimeoutException, java.util.concurrent.TimeoutException, InterruptedException, MemcachedException{
		taskService.setMemcachedClient(memcachedClient);
		String uid = "abc";
		String tid1 = "d29217ebaec34d678b90929c46a4c6cd";
		String tid2 = "testTid";
		String key = String.format(TaskServiceImpl.MEMCACHED_TASK_REPEATED, uid, tid2);
		when(memcachedClient.get(key)).thenReturn(100);
		Integer value1 = taskService.getUserTaskInfo(uid, tid1);
		Integer value2 = taskService.getUserTaskInfo(uid, tid2);
		assertThat(value1, equalTo(0));
		assertThat(value2, equalTo(100));
	}

}
*/
