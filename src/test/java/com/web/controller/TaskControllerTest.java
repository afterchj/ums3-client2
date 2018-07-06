package com.web.controller;

import java.util.List;
import java.util.Map;

import com.dao.SPItemDao;
import com.model.SPItem;
import com.model.dd.ResultDict;
import com.service.SPItemService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.utils.Constants;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TaskControllerTest {
	static  ClassPathXmlApplicationContext ctx;
	 static  {
		 ctx = new ClassPathXmlApplicationContext("conf/beans.xml", "conf/spring-servlet.xml");
	}


	@Test
	public void test() {
		if (Constants.propertiesLoader.getBoolean(
				"task.data.info.oldversion.fake", false)) {
			Map<String, Map<String, List<String>>> oldVer357DataMap = simpleOldVer357Compatibility();
			Map<String, Map<String, List<String>>> dataInfo = testCompatibility();
			if (MapUtils.isNotEmpty(dataInfo)) {
				for (String tid : dataInfo.keySet()) {
					Map<String, List<String>> taskDataInfo = dataInfo.get(tid);
					if (StringUtils.equalsIgnoreCase("f4ca48cf5df2477eb7fe67780fdcd1ac", tid)) {
						if (MapUtils.isNotEmpty(taskDataInfo)) {
							Map<String, List<String>> oldVer357TaskDataMap = oldVer357DataMap.get(tid);
							oldVer357TaskDataMap.putAll(taskDataInfo);
						}
					} else {
						oldVer357DataMap.put(tid, taskDataInfo);
					}
				}
			}
			System.out.println(JSONObject.toJSONString(oldVer357DataMap));
		}
	}

	private Map<String, Map<String, List<String>>> simpleOldVer357Compatibility() {
		Map<String, Map<String, List<String>>> oldVerDataInfo = Maps.newLinkedHashMap();
		Map<String, List<String>> dataInfo = Maps.newLinkedHashMap();
		dataInfo.put("snapshot-cpa-1", Lists.newArrayList("inst, open_2"));
		dataInfo.put("snapshot-cpa-2", Lists.newArrayList("inst, open_2"));
		oldVerDataInfo.put("f4ca48cf5df2477eb7fe67780fdcd1ac", dataInfo);
		return oldVerDataInfo;
	}
	
	private Map<String, Map<String, List<String>>> testCompatibility() {
		Map<String, Map<String, List<String>>> oldVerDataInfo = Maps
				.newHashMap();
		Map<String, List<String>> dataInfo = Maps.newHashMap();
		dataInfo.put("snapshot-cpa-3", Lists.newArrayList("inst, open_2"));
		dataInfo.put("snapshot-cpa-4", Lists.newArrayList("inst, open_2"));
		oldVerDataInfo.put("f4ca48cf5df2477eb7fe67780fdcd1ac", dataInfo);
		return oldVerDataInfo;
	}



}
