package com.web.vo;

import java.io.Serializable;

public class GoodsShelf implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private Integer sort;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
