package com.model;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.common.collect.Lists;

//@Entity
//@DiscriminatorValue("diy")
public class Diy extends CateItem {

	private List<ThemeFile> files = Lists.newArrayList();
	private List<DiyInfo> infos = Lists.newArrayList();

//	@ManyToMany(fetch = FetchType.LAZY)
//	@JoinTable(name = "f_category_file", joinColumns = { @JoinColumn(name = "category_id") }, inverseJoinColumns = { @JoinColumn(name = "file_id") })
	public List<ThemeFile> getFiles() {
		return files;
	}

	public void setFiles(List<ThemeFile> files) {
		this.files = files;
	}

//	@OneToMany(mappedBy = "diy", fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE }, orphanRemoval = true)
	public List<DiyInfo> getInfos() {
		return infos;
	}

	public void setInfos(List<DiyInfo> infos) {
		this.infos = infos;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
