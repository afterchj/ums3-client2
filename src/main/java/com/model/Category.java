package com.model;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.common.collect.Lists;

//category
public class Category extends CateItem {
	private static final long serialVersionUID = 1L;
	private List<ThemeFile> files = Lists.newArrayList();
	private List<CategoryInfo> infos = Lists.newArrayList();

	public List<ThemeFile> getFiles() {
		return files;
	}

	public void setFiles(List<ThemeFile> files) {
		this.files = files;
	}

	public List<CategoryInfo> getInfos() {
		return infos;
	}

	public void setInfos(List<CategoryInfo> infos) {
		this.infos = infos;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
