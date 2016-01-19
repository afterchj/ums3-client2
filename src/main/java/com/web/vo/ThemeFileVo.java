package com.web.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class ThemeFileVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String title;
	private Long uxSize;
	private Long apkSize;
	private BigDecimal price;
	private String uxHvga;
	private String uxWvga;
	private String apkPath;
	private String uxPath;
	private String dtype;
	private String marketURL;
	private String version;
	private String iconPath;
	private String adPath;
	private String preWebPath;
	private String preClientPath;
	private String createTime;
	private String modifyTime;

	private String downloadURL;
	private Long totalDownload;
	private Long ishot;
	private Long isnew;
	private Long percent;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getUxSize() {
		return uxSize;
	}

	public void setUxSize(Long uxSize) {
		this.uxSize = uxSize;
	}

	public Long getApkSize() {
		return apkSize;
	}

	public void setApkSize(Long apkSize) {
		this.apkSize = apkSize;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getUxHvga() {
		return uxHvga;
	}

	public void setUxHvga(String uxHvga) {
		this.uxHvga = uxHvga;
	}

	public String getUxWvga() {
		return uxWvga;
	}

	public void setUxWvga(String uxWvga) {
		this.uxWvga = uxWvga;
	}

	public String getApkPath() {
		return apkPath;
	}

	public void setApkPath(String apkPath) {
		this.apkPath = apkPath;
	}

	public String getUxPath() {
		return uxPath;
	}

	public void setUxPath(String uxPath) {
		this.uxPath = uxPath;
	}

	public String getDtype() {
		return dtype;
	}

	public void setDtype(String dtype) {
		this.dtype = dtype;
	}

	public String getMarketURL() {
		return marketURL;
	}

	public void setMarketURL(String marketURL) {
		this.marketURL = marketURL;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public String getAdPath() {
		return adPath;
	}

	public void setAdPath(String adPath) {
		this.adPath = adPath;
	}

	public String getPreWebPath() {
		return preWebPath;
	}

	public void setPreWebPath(String preWebPath) {
		this.preWebPath = preWebPath;
	}

	public String getPreClientPath() {
		return preClientPath;
	}

	public void setPreClientPath(String preClientPath) {
		this.preClientPath = preClientPath;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getDownloadURL() {
		return downloadURL;
	}

	public void setDownloadURL(String downloadURL) {
		this.downloadURL = downloadURL;
	}

	public Long getIshot() {
		return ishot;
	}

	public void setIshot(Long ishot) {
		this.ishot = ishot;
	}

	public Long getIsnew() {
		return isnew;
	}

	public void setIsnew(Long isnew) {
		this.isnew = isnew;
	}

	public Long getPercent() {
		return percent;
	}

	public void setPercent(Long percent) {
		this.percent = percent;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getTotalDownload() {
		return totalDownload;
	}

	public void setTotalDownload(Long totalDownload) {
		this.totalDownload = totalDownload;
	}
	
}
