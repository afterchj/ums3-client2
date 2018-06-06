package com.web.vo;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.common.collect.Lists;

public class CategoryVo extends CateItemVo {

	private static final long serialVersionUID = 1L;
	
	private List<ThemeFileVo> files = Lists.newArrayList();
	private List<CategoryInfoVo> infos = Lists.newArrayList();

	public List<ThemeFileVo> getFiles() {
		return files;
	}

	public void setFiles(List<ThemeFileVo> files) {
		this.files = files;
	}

	public List<CategoryInfoVo> getInfos() {
		return infos;
	}

	public void setInfos(List<CategoryInfoVo> infos) {
		this.infos = infos;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
