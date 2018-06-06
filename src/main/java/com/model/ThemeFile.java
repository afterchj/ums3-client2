package com.model;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.common.collect.Lists;
import com.utils.ConvertUtils;

/**
 * 文件基础关系实体
 *
 * @author ken.cui
 */
public class ThemeFile extends IdEntity {
	private static final long serialVersionUID = 1L;
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

    private List<FileTag> fileTags = Lists.newArrayList();

    private List<ThemeThirdURL> thirdURLs = Lists.newArrayList();
    private List<Category> categories = Lists.newArrayList();
    private List<FileInfo> fileInfo = Lists.newArrayList();
    private List<FileStoreInfo> infoStore = Lists.newArrayList();
    private List<ShelfFileLink> shelfFiles = Lists.newArrayList();
    private List<TopicFileLink> topicFiles=Lists.newArrayList();
    private List<FileMarketValue> marketValues = Lists.newArrayList();
    private List<Store> stores = Lists.newArrayList();

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

    public Long getPercent() {
        return percent;
    }

    public void setPercent(Long percent) {
        this.percent = percent;
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
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

    public String getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }

    public List<ThemeThirdURL> getThirdURLs() {
        return thirdURLs;
    }

    public void setThirdURLs(List<ThemeThirdURL> thirdURLs) {
        this.thirdURLs = thirdURLs;
    }

    public List<FileInfo> getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(List<FileInfo> fileInfo) {
        this.fileInfo = fileInfo;
    }

    public List<FileStoreInfo> getInfoStore() {
        return infoStore;
    }

    public void setInfoStore(List<FileStoreInfo> infoStore) {
        this.infoStore = infoStore;
    }

    public List<ShelfFileLink> getShelfFiles() {
        return shelfFiles;
    }

    public void setShelfFiles(List<ShelfFileLink> shelfFiles) {
        this.shelfFiles = shelfFiles;
    }

    public List<TopicFileLink> getTopicFiles() {
        return topicFiles;
    }

    public void setTopicFiles(List<TopicFileLink> topicFiles) {
        this.topicFiles = topicFiles;
    }

    public List<FileMarketValue> getMarketValues() {
        return marketValues;
    }

    public void setMarketValues(List<FileMarketValue> marketValues) {
        this.marketValues = marketValues;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }

    public List<FileTag> getFileTags() {
        return fileTags;
    }

    public void setFileTags(List<FileTag> fileTags) {
        this.fileTags = fileTags;
    }

    public String getTagNames() {
        return ConvertUtils.convertElementPropertyToString(fileTags, "name", ",");
    }

    public List<Long> getCheckedTagIds(){
        return ConvertUtils.convertElementPropertyToList(fileTags,"id");
    }

    @SuppressWarnings("unchecked")
    public List<Long> getCheckedCategoryIds() {
        return ConvertUtils.convertElementPropertyToList(categories, "id");
    }

    @SuppressWarnings("unchecked")
    public List<Long> getCheckedStoreIds() {
        return ConvertUtils.convertElementPropertyToList(stores, "id");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        ThemeFile that = (ThemeFile) obj;
        return that.getId().equals(this.getId());
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

	public Long getTotalDownload() {
		return totalDownload;
	}

	public void setTotalDownload(Long totalDownload) {
		this.totalDownload = totalDownload;
	}

}
