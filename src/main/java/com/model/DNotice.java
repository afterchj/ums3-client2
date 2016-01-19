package com.model;

import java.util.Date;

//1	id	string	32	no	公告名称
//2	type	int		no	订单类型 0.系统 1.活动 2. 荣耀榜 3.邀请收入
//3	publishDate	date		no	公告生成时间
//4	name	string	50	no	公告名称
//5	detail	string 	255	no	公告具体描述（如果为webview时， 为跳转地址）
//6	dtype	int	2	no	1. webview  2. native
//7 startTime datetime no 
//8 endTime datetime   no

public class DNotice {

	private String id;
	private Integer type;
	private Date publishDate;
	private String name;
	private String detail;
	private Integer dtype;
	private String uid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Integer getDtype() {
		return dtype;
	}

	public void setDtype(Integer dtype) {
		this.dtype = dtype;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

}
