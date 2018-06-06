package com.web.vo;

import java.io.Serializable;

public class StoreVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected String name;
	protected String value;
	protected String icon;
	protected String description;

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
}
