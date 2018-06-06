package com.model;

import java.io.Serializable;

/**
 * Created by yuanjie.fang on 2018/1/11.
 * 视频文件实体类
 */
public class SPFile implements Serializable {

    private static final long serialVersionUID = -4370812521053500481L;
    private Integer id;
    //视频名称
    private String name;
    //视频大小(单位：M)
    private Double size;
    //视频时长(单位：s)
    private Integer duration;
    //视频下载地址
    private String downloadPath;
    //视频封面地址
    private String iconPath;
    //视频标签
    private String lable;
    //创建时间
    private String createTime;
    //修改时间
    private String modifyTime;
    //是否最新(0-不是(默认)，1-是)
    private Integer isHot;
    //是否最热(0-不是(默认)，1-是)
    private Integer isNew;
    //是否推荐(0-不推荐(默认)，1-推荐)
    private Integer isRecommend;
    //其他(预留字段)
    private String other;
    //下载量
    private Integer totalDownload;


    public SPFile() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
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

    public Integer getIsHot() {
        return isHot;
    }

    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }

    public Integer getIsNew() {
        return isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    public Integer getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Integer isRecommend) {
        this.isRecommend = isRecommend;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public Integer getTotalDownload() {
        return totalDownload;
    }

    public void setTotalDownload(Integer totalDownload) {
        this.totalDownload = totalDownload;
    }

    @Override
    public String toString() {
        return "SPFile{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", size=" + size +
                ", duration=" + duration +
                ", downloadPath='" + downloadPath + '\'' +
                ", iconPath='" + iconPath + '\'' +
                ", lable='" + lable + '\'' +
                ", createTime='" + createTime + '\'' +
                ", modifyTime='" + modifyTime + '\'' +
                ", isHot=" + isHot +
                ", isNew=" + isNew +
                ", isRecommend=" + isRecommend +
                ", other='" + other + '\'' +
                ", totalDownload=" + totalDownload +
                '}';
    }
}
