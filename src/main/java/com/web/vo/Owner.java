package com.web.vo;

import java.io.Serializable;

public class Owner implements Serializable {

	public Owner(String id, Integer visitors, String descr) {
		super();
		this.id = id;
		this.visitors = visitors;
		this.descr = descr;
	}

	private static final long serialVersionUID = 1L;
	private String id;
	private Integer visitors;
	private String descr;

	public Integer getVisitors() {
		return visitors;
	}

	public void setVisitors(Integer visitors) {
		this.visitors = visitors;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
