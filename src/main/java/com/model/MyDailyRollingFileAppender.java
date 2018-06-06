package com.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.DailyRollingFileAppender;

public class MyDailyRollingFileAppender extends DailyRollingFileAppender{
	static {
		try {
			String env = System.getenv("LOG4J_SUFFIX");
			if(StringUtils.isBlank(env)){
				env = "ums35M1";
			}
			System.setProperty("LOG4J_SUFFIX", env);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
