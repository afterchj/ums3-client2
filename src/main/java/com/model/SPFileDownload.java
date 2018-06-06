package com.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yuanjie.fang on 2018/1/11.
 * 统计视频下载量
 */
public class SPFileDownload implements Serializable {

    private static final long serialVersionUID = 6525022568948009594L;
    private Integer id;
    private Date createDate;
    private String logDate;
    private Integer fildId;
    private Integer totalDownload;
    private Integer totalVisit;

    public SPFileDownload() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getLogDate() {
        return logDate;
    }

    public void setLogDate(String logDate) {
        this.logDate = logDate;
    }

    public Integer getFildId() {
        return fildId;
    }

    public void setFildId(Integer fildId) {
        this.fildId = fildId;
    }

    public Integer getTotalDownload() {
        return totalDownload;
    }

    public void setTotalDownload(Integer totalDownload) {
        this.totalDownload = totalDownload;
    }

    public Integer getTotalVisit() {
        return totalVisit;
    }

    public void setTotalVisit(Integer totalVisit) {
        this.totalVisit = totalVisit;
    }

    @Override
    public String toString() {
        return "SPFileDownload{" +
                "id=" + id +
                ", createDate=" + createDate +
                ", logDate='" + logDate + '\'' +
                ", fildId=" + fildId +
                ", totalDownload=" + totalDownload +
                ", totalVisit=" + totalVisit +
                '}';
    }
}
