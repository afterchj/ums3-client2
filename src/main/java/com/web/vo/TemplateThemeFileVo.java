package com.web.vo;

import java.io.Serializable;

public class TemplateThemeFileVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String title;
	private Long uxSize;
	private String uxPath;
	private String iconPath;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getUxSize() {
		return uxSize;
	}

	public void setUxSize(Long uxSize) {
		this.uxSize = uxSize;
	}

	public String getUxPath() {
		return uxPath;
	}

	public void setUxPath(String uxPath) {
		this.uxPath = uxPath;
	}


	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
