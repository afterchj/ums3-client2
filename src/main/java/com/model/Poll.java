package com.model;

import java.util.Date;

public class Poll {

	private Long id;
	private String uuid;
	private String dtype;
	private String store;
	private String contentName;
	private String contentInfo;
	private String fm;
	private String appk;
	private String appUrl;
	private String imgName;
	private String imgLink;
	private Long status;
	private Long percent;
	private Date createTime;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getDtype() {
		return dtype;
	}

	public void setDtype(String dtype) {
		this.dtype = dtype;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public String getContentName() {
		return contentName;
	}

	public void setContentName(String contentName) {
		this.contentName = contentName;
	}

	public String getContentInfo() {
		return contentInfo;
	}

	public void setContentInfo(String contentInfo) {
		this.contentInfo = contentInfo;
	}

	public String getFm() {
		return fm;
	}

	public void setFm(String fm) {
		this.fm = fm;
	}

	public String getAppk() {
		return appk;
	}

	public void setAppk(String appk) {
		this.appk = appk;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String getImgLink() {
		return imgLink;
	}

	public void setImgLink(String imgLink) {
		this.imgLink = imgLink;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getPercent() {
		return percent;
	}

	public void setPercent(Long percent) {
		this.percent = percent;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "PollEnhancement{" + "uuid='" + uuid + '\'' + ", dtype='"
				+ dtype + '\'' + ", store='" + store + '\'' + ", contentName='"
				+ contentName + '\'' + ", fm='" + fm + '\'' + ", appk='" + appk
				+ '\'' + ", appURL='" + appUrl + '\'' + ", imageName='"
				+ imgName + '\'' + ", imgLink='" + imgLink + '\''
				+ ", percent=" + percent + ", createTime=" + createTime + '}';
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
