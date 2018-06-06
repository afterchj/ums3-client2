package com.model.log;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.model.IdEntity;

/**
 * 客户端和内容捆绑安装统计Entity
 *
 * @author ken.cui
 */
//@Entity
//@Table(name = "log_count_c_install_with_content")
public class LogCountClientInstallWithContent extends IdEntity {

    private String date;
    private String appName;
    private String marketName;
    private Long installed;
    private Date createTime;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public Long getInstalled() {
        return installed;
    }

    public void setInstalled(Long installed) {
        this.installed = installed;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof LogCountClientInstallWithContent)) {
            return false;
        }
        LogCountClientInstallWithContent that = (LogCountClientInstallWithContent) obj;
        return this.date.equals(that.date) && this.appName.equals(that.appName) && this.marketName.equals(that.marketName);
    }

    @Override
    public int hashCode() {
        int result = 37;
        result += date.hashCode() * 17;
        result += appName.hashCode() * 17;
        result += marketName.hashCode() * 17;
        return result;
    }

    @Override
    public String toString() {

        return ToStringBuilder.reflectionToString(this);
    }
}
