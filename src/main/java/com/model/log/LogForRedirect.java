package com.model.log;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.model.IdEntity;

//@Entity
//@Table(name = "log_f_redirect")
public class LogForRedirect extends IdEntity {

	private String appName;
	private String linkAddr;
	private Date createTime;

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this);
	}
}
