package com.model;


public class CateItem extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String name;
	private String value;
	private String icon;
	private String description;
	private String dtype;
	private CateItem parent;

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

	public String getDtype() {
		return dtype;
	}

	public void setDtype(String dtype) {
		this.dtype = dtype;
	}

	public CateItem getParent() {
		return parent;
	}

	public void setParent(CateItem parent) {
		this.parent = parent;
	}

}
