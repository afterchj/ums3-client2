package com.web.vo;

import com.alibaba.fastjson.JSONObject;

public class WorkNewUploadVo {
	
	private String shared;
	private String descr;
	private String uid;

	public String getShared() {
		return shared;
	}

	public void setShared(String shared) {
		this.shared = shared;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public static WorkNewUploadVo parseFrom(JSONObject params) {
		WorkNewUploadVo vo = new WorkNewUploadVo();
		vo.setUid(params.getString("uid"));
		vo.setDescr(params.getString("descr"));
		vo.setShared(params.getString("shared"));
		return vo;
	}

}
