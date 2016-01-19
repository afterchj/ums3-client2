package com.web.vo;

import java.util.Date;

public class Notice {

	private String id;
	private Integer type;
	private Date publishDate;
	private String name;
	private String detail;
	private Integer dtype;

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

}
