package com.web.controller;


import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.model.ThirdLogin;
import com.tpadsz.uic.user.api.vo.TpadUser;
import com.uicdao.ThirdLoginDao;
import com.utils.Constants;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Map;

public class TaskControllerTest {
	static  ClassPathXmlApplicationContext ctx;
	static{
		ctx = new ClassPathXmlApplicationContext("classpath:conf/beans.xml");
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


	ThirdLoginDao thirdLoginDao = ctx.getBean("thirdLoginDao", ThirdLoginDao.class);
	@Test
	public void test2(){
		ThirdLogin third = new ThirdLogin();
		third.setId("ba1d14a0fe0040808d7ee6612c25385e");
		third.setQq("1234");
		third.setQq_nickname("kaikai");
		third.setQq_image_url("http:123.jpg");
//		third.setWx("234");
//		third.setWx_image_url("http:www.2345.jpg");
		Map<String, Object> map = thirdLoginDao.getUserInfoById(third.getId());

		com.tpadsz.uic.user.api.vo.AppUser appUser = new com.tpadsz.uic.user
				.api.vo.AppUser();
		TpadUser tpadUser = new TpadUser();
		appUser.setTpadUser(tpadUser);
		appUser.setId((String) map.get("id"));
		if (map.get("icon")!=null){
			appUser.setIcon((String) map.get("icon"));
		}
		if (map.get("nickname")!=null){
			appUser.setNickname((String) map.get("nickname"));
		}
		if (map.get("birthday")!=null){
			appUser.getTpadUser().setBirthday((Integer) map.get("birthday"));
		}
		if (map.get("birthmonth")!=null){
			appUser.getTpadUser().setBirthmonth((Integer) map.get("birthmonth"));
		}
		if (map.get("birthyear")!=null){
			appUser.getTpadUser().setBirthyear((Integer) map.get("birthyear"));
		}
		if (map.get("login_name")!=null){
			appUser.setLoginName((String) map.get("login_name"));
		}
		if (map.get("mobile")!=null){
			appUser.getTpadUser().setMobile((String) map.get("mobile"));
		}

		if (map.get("prov")!=null){
			appUser.getTpadUser().setProv((Integer) map.get("prov"));
		}
		if (map.get("gender")!=null){
			appUser.getTpadUser().setGender((Integer) map.get("gender"));
		}
		System.out.println(JSONObject.toJSON(appUser));
	}


}
