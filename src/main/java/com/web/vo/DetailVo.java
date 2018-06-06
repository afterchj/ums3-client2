package com.web.vo;

import java.io.Serializable;

public class DetailVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	// 展示用标题.
	private String title;
	// 锁屏标签.
	private String label;
	// 作品描述
	private String descr;
	// 作者
	private String author;
	// 大小
	private Long size;
	private Long uxSize;
	// 图标
	private String icon;
	// 预览图1
	private String lockedPreView;
	// 预览图2
	private String unlockedPreView;
	private String downloadUrl;

	private String downloadUxUrl;
	// 下载次数
	private String downloadTimes;
	// 0 : 普通锁屏 1: Diy锁屏
	private String dtype;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getLockedPreView() {
		return lockedPreView;
	}

	public void setLockedPreView(String lockedPreView) {
		this.lockedPreView = lockedPreView;
	}

	public String getUnlockedPreView() {
		return unlockedPreView;
	}

	public void setUnlockedPreView(String unlockedPreView) {
		this.unlockedPreView = unlockedPreView;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getDownloadTimes() {
		return downloadTimes;
	}

	public void setDownloadTimes(String downloadTimes) {
		this.downloadTimes = downloadTimes;
	}

	public String getDtype() {
		return dtype;
	}

	public void setDtype(String dtype) {
		this.dtype = dtype;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getDownloadUxUrl() {
		return downloadUxUrl;
	}

	public void setDownloadUxUrl(String downloadUxUrl) {
		this.downloadUxUrl = downloadUxUrl;
	}

	public Long getUxSize() {
		return uxSize;
	}

	public void setUxSize(Long uxSize) {
		this.uxSize = uxSize;
	}

}
