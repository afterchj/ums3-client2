package com.web.vo;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.dozer.Mapping;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("poll")
public class PollForm implements Serializable{
	private static final long serialVersionUID = 1L;
	@XStreamAsAttribute
	@XStreamAlias("id")
	private String uuid;
	private String type;
	private String contentName;
	private String fm;
	private String pkg;
	private String appDownUrl;
	private String imageName;
	private String imageDownUrl;
	private String percent;
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Mapping("dtype")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContentName() {
		return contentName;
	}

	public void setContentName(String contentName) {
		this.contentName = contentName;
	}

	public String getFm() {
		return fm;
	}

	public void setFm(String fm) {
		this.fm = fm;
	}

	@Mapping("appk")
	public String getPkg() {
		return pkg;
	}

	public void setPkg(String pkg) {
		this.pkg = pkg;
	}

	@Mapping("appUrl")
	public String getAppDownUrl() {
		return appDownUrl;
	}

	public void setAppDownUrl(String appDownUrl) {
		this.appDownUrl = appDownUrl;
	}

	@Mapping("imgName")
	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	@Mapping("imgLink")
	public String getImageDownUrl() {
		return imageDownUrl;
	}

	public void setImageDownUrl(String imageDownUrl) {
		this.imageDownUrl = imageDownUrl;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
