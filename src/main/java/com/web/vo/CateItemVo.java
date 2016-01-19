package com.web.vo;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class CateItemVo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String value;
	private String icon;
	private String description;
	private CateItemVo parent;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CateItemVo getParent() {
		return parent;
	}

	public void setParent(CateItemVo parent) {
		this.parent = parent;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
