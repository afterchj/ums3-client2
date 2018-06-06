package com.web.vo;

import java.io.Serializable;


public class TemplateFileLinkVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private ThemeFileVo theme;
	private TemplateVo template;
	private Long sort;

	public ThemeFileVo getTheme() {
		return theme;
	}

	public void setTheme(ThemeFileVo theme) {
		this.theme = theme;
	}

	public TemplateVo getTemplate() {
		return template;
	}

	public void setTemplate(TemplateVo template) {
		this.template = template;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
}
