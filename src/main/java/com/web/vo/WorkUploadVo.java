package com.web.vo;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

public class WorkUploadVo implements Serializable{

	private static final long serialVersionUID = 1L;

	private String nickname;
	private String descr;
	private String shared;
	private String id;
	private String loginName;
	private String type;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getShared() {
		return shared;
	}

	public void setShared(String shared) {
		this.shared = shared;
	}

	public static WorkUploadVo parseFrom(String data)
			throws UnsupportedEncodingException {
		return JSON.parseObject(data, WorkUploadVo.class);
	}

	public boolean hasMemberId() {
		return StringUtils.isNotBlank(type) && StringUtils.isNotBlank(loginName);
	}

	public boolean hasDescr() {
		return StringUtils.isNotBlank(descr);
	}

	public boolean hasNickname() {
		return StringUtils.isNotBlank(nickname);
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
