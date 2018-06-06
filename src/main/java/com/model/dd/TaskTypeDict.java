package com.model.dd;

public enum TaskTypeDict {
	
	SIGN_IN("001","签到任务"), QQ_SHARE("002", "qq分享任务"), WEIXIN_SHARE("003", "微信分享任务"), 
	CPA("004", "CPA"), CPC("005", "CPC"), LOTTERY("006", "抢红包任务"), PRIZE("007", "送奖品");

	TaskTypeDict(String code, String value) {
		this.value = value;
		this.code = code;
	}

	private String value;
	private String code;

	public String getValue() {
		return value;
	}

	public String getCode() {
		return code;
	}
}
