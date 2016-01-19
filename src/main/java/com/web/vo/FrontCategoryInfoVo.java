package com.web.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FrontCategoryInfoVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private String description;
	
	private String value;
	
	private String type;
	
	private List<FrontThemeFileVo> frontThemeFileVoList = new ArrayList<FrontThemeFileVo>();

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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<FrontThemeFileVo> getFrontThemeFileVoList() {
		return frontThemeFileVoList;
	}

	public void setFrontThemeFileVoList(List<FrontThemeFileVo> frontThemeFileVoList) {
		this.frontThemeFileVoList = frontThemeFileVoList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
