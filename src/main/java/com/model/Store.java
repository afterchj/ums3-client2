package com.model;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.common.collect.Lists;
import com.utils.ConvertUtils;

public class Store extends CateItem {
	private static final long serialVersionUID = 1L;
	private List<FileStoreInfo> fileStoreInfo = Lists.newArrayList();
	private List<Shelf> shelfs = Lists.newArrayList();

	private List<ThemeFile> themes = Lists.newArrayList();

	public List<Shelf> getShelfs() {
		return shelfs;
	}

	public void setShelfs(List<Shelf> shelfs) {
		this.shelfs = shelfs;
	}

	public List<FileStoreInfo> getFileStoreInfo() {
		return fileStoreInfo;
	}

	public void setFileStoreInfo(List<FileStoreInfo> fileStoreInfo) {
		this.fileStoreInfo = fileStoreInfo;
	}

    public List<ThemeFile> getThemes() {
		return themes;
	}
	
	public void setThemes(List<ThemeFile> themes) {
		this.themes = themes;
	}

	public String getCategoryNames() {
		return ConvertUtils.convertElementPropertyToString(shelfs, "name", ",");
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		Store that = (Store) obj;
		return that.getName().equals(this.getName());
	}

	@Override
	public int hashCode() {
		assert false : "hashCode not designed";
		return 42; // any arbitrary constant will do
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this);
	}
}
