package com.web.vo;

import java.io.Serializable;

public class TemplateVo implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
//	private String value;
	private String description;
//	private String icon;
	private Long sort;
//	private List<TemplateFileLinkVo> templateFileLinkList = Lists.newArrayList();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
