package com.utils;

import java.io.File;
import java.util.Calendar;
import java.util.Date;


public class Constants {
	public static PropertiesLoader propertiesLoader = new PropertiesLoader("classpath:/setup.properties");
	
	public static final String SYSTEM_VERSION = propertiesLoader.getProperty("system.version");
	
	public static final int ENABLED = 1;
	public static final int DISABLED = 0;
	
	public static final int ALLOWED = 1;
	public static final int DENIED = 0;
	
	public static final String HAS_MORE = "1";
	public static final String NO_MORE = "0";
	
	public static final String NORMAL_LOCK = "0";
	public static final String DIY_LOCK = "1";
	
	public static final long ONEDAY_MILLISECONDS = 24l * 60 * 60 * 1000;
	
	public static String STATIC_SHOW;
	
	//服务器tomcat布置地址.
	public static String TOMCAT_SHOW;

	public static String HEADS_STORAGE;
	
	public static final Integer CACHE_MEMCACHED_TIMEOUT = propertiesLoader.getInteger("cache.memcached.timeout", 300);
	
	public static final String BROADCASTTYPE = propertiesLoader.getProperty("broadcasttypes");
	
	public static final String CATEGORY_LIST = propertiesLoader.getProperty("categorylist");
	
	//public static final String BROADCASTTYPE = "inst,open_2,serial_open_0,serial_open_1,serial_open_2,serial_open_3,serial_open_4";
	
	public static File WORK_PIC = null;
	
	public static final String APP_ID = "1";
	
	public static String CTC_SETTING ;
	
	public static Date CTC_TASKS_STARTTIME;
	
	public static Date CTC_TASKS_ENDTIME;
	
	static {
		Calendar calendar = Calendar.getInstance();
		if(calendar.get(Calendar.HOUR_OF_DAY) < 5){
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		}
		calendar.set(Calendar.HOUR_OF_DAY, 5);
		calendar.set(Calendar.MINUTE, 0);
		Constants.CTC_TASKS_STARTTIME = calendar.getTime();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		Constants.CTC_TASKS_ENDTIME = calendar.getTime();
	}
	//任务ID
	//邀请任务
	public static final String TASK_RECOMMEND = "1";
	//注册任务
	public static final String TASK_REGISTER = "2";
	
	public static final String TASK_SHELF_USER = "1";
	
	public static final int ORDER_DEALING = 0;
	
	public static final int ORDER_SUCC = 1;
	
	public static final int ORDER_FAIL = 2;
	
	public static final String COIN_TYPE_GOODS = "goods";
	
	public static final String COIN_TYPE_TASK = "task";
	
	public static final int NOTICE_TYPE_SYSTEM = 0;
	
	public static final int NOTICE_TYPE_ACTIVITY = 1;
	
	public static final int NOTICE_TYPE_GLORIOUS = 2;
	
	public static final int NOTICE_TYPE_RECOMMEND = 3;
	
	public static final int NOTICE_DTYPE_WEBVIEW = 1;
	
	public static final int NOTICE_DTYPE_NATIVE = 2;
	
	public static final String COINS_PRIZE = "170";
	
	public static final String COINS_TASK = "167";
	
	public static final String BACKGROUND_ENCRPTION_KEY = "encrypted";
	
	public static final String NOTICE_INVITATION_PRIZE = propertiesLoader.getProperty("notice.invitation.prize");
	
	public static final String NOTICE_WELCOME_CONTENT = propertiesLoader.getProperty("notice.welcome.content");
	
	public static final String NOTICE_WELCOME_NAME = propertiesLoader.getProperty("notice.welcome.name");
	
	public static final String NOTICE_REGISTER_NAME = propertiesLoader.getProperty("notice.register.name");
	
	public static final String NOTICE_REGISTER_CONTENT = propertiesLoader.getProperty("notice.register.content");
	
	//3des加密Code
	public static final String DES_CRYPT_KEY = propertiesLoader.getProperty("des.encryption.key");
	
	//============客户端参数===========================================================//
	public static final String PARA_LANGUAGE = "l";
	public static final String PARA_DOWNLOAD_METHOD = "dm";
	public static final String PARA_FROM_MARKET = "fm";
	public static final String PARA_IMEI = "imei";
	public static final String PARA_IMSI = "imsi";
	public static final String PARA_STORE_TYPE = "st";
	public static final String PARA_CLIENT_VERSION = "v";
	public static final String PARA_CONTENT_VERSION = "cv";
	public static final String PARA_CLIENT_TYPE = "ct";
	public static final String PARA_RESOLUTION = "r";
	public static final String PARA_OPERATORS = "op";
	public static final String PARA_NET_ENVIRONMENT = "net";
	public static final String PARA_AUTO_SWITCH = "as";
	public static final String PARA_SAFETYLOCK = "sl";
	public static final String PARA_MACHINE_MODEL = "model";
	public static final String PARA_DO_TYPE = "do";
	public static final String PARA_APP_NAME = "app";
	public static final String PARA_BCID = "bcid";
	public static final String PARA_URL = "url";
	public static final String PARA_FROM = "f";
	public static final String PARA_GENDER = "g";
	
	public enum Language {
		ZH("zh"), EN("en"), JP("jp"), JA("ja");
		private String value;

		Language(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
	
	public static enum Type {
		RECOMMEND("recommended", "推荐"),

		HOTTEST("hottest", "最热"),

		GAME("game", "游戏"),

		STAR("star", "星座"),

		NEWEST("newest", "最新"),
		
		APP("app", "软件");

		public String value;
		public String displayName;

		Type(String value, String displayName) {
			this.value = value;
			this.displayName = displayName;
		}

		public String getValue() {
			return value;
		}

		public String getDisplayName() {
			return displayName;
		}
	}
	
}
