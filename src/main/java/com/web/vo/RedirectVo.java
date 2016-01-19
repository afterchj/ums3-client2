package com.web.vo;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

public class RedirectVo implements Serializable{

	private static final long serialVersionUID = 1L;
	private String appName;
	private String linkAddr;
	private String createDate;
	private String dtype;
	private String pkg = "redirect";
	private String clazz;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getLinkAddr() {
		return linkAddr;
	}

	public void setLinkAddr(String linkAddr) {
		this.linkAddr = linkAddr;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getDtype() {
		return dtype;
	}

	public void setDtype(String dtype) {
		this.dtype = dtype;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

	public String getPkg() {
		return pkg;
	}

	public void setPkg(String pkg) {
		this.pkg = pkg;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
}
