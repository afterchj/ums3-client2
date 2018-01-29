package com.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yuanjie.fang on 2017/11/2.
 * 统计主题下载量和访问量
 */
public class DLogCountStore implements Serializable {
    private static final long serialVersionUID = 4279720970446164994L;

    private String id;
    private Date createDate;
    private String logDate;
    private String themeName;
    private Integer totalDown;
    private Integer totalVisit;

    public DLogCountStore() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public Integer getTotalDown() {
        return totalDown;
    }

    public void setTotalDown(Integer totalDown) {
        this.totalDown = totalDown;
    }

    public Integer getTotalVisit() {
        return totalVisit;
    }

    public void setTotalVisit(Integer totalVisit) {
        this.totalVisit = totalVisit;
    }

    @Override
    public String toString() {
        return "DLogCountStore{" +
                "id='" + id + '\'' +
                ", createDate=" + createDate +
                ", logDate='" + logDate + '\'' +
                ", themeName='" + themeName + '\'' +
                ", totalDown=" + totalDown +
                ", totalVisit=" + totalVisit +
                '}';
    }
}
