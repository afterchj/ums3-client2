package com.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class UserFeedback extends IdEntity {

	private static final long serialVersionUID = 1L;
	private String content;
	private String contact;
	private String params;
	private String status;
	private Date createTime;
	private Date modifyTime;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this);
	}
}
