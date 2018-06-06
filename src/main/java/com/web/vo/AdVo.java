package com.web.vo;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.dozer.Mapping;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("ad")
public class AdVo implements Serializable {
	@XStreamOmitField
	private static final long serialVersionUID = 1L;

	@XStreamAsAttribute
	private String id;

	@XStreamAsAttribute
	private String fileName;

	@XStreamAsAttribute
	private String format;

	@XStreamAsAttribute
	private String version;
	@XStreamAlias("linkUrl")
	private String linkUrl;
	@XStreamAlias("downloadUrl")
	private String downloadUrl;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Mapping("imgName")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Mapping("imgExt")
	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Mapping("link")
	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	@Mapping("imgLink")
	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
