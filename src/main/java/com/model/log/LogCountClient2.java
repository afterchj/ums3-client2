package com.model.log;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.model.IdEntity;

/**
 * User: ken.cui
 * Date: 13-4-17
 * Time: 下午1:02
 */
//@Entity
//@Table(name="log_count_client")
public class LogCountClient2 extends IdEntity{
    private String createTime;
    private Long openCount;
    private Long totalUser;
    private Long openUser;
    private Long incrementUser;
    private Long totalDownload;
    private Long downByContent;
    private Long downByShare;
    private Long downByOther;
    private Long visitStoreCount;
    private Long visitStoreUser;
    private Long totalInstall;
    private Long installWithfm;
    private Long installNonfm;
    private Long installUser;
    private Long takeTimes;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getOpenCount() {
        return openCount;
    }

    public void setOpenCount(Long openCount) {
        this.openCount = openCount;
    }

    public Long getTotalUser() {
        return totalUser;
    }

    public void setTotalUser(Long totalUser) {
        this.totalUser = totalUser;
    }

    public Long getOpenUser() {
        return openUser;
    }

    public void setOpenUser(Long openUser) {
        this.openUser = openUser;
    }

    public Long getIncrementUser() {
        return incrementUser;
    }

    public void setIncrementUser(Long incrementUser) {
        this.incrementUser = incrementUser;
    }

    public Long getTotalDownload() {
        return totalDownload;
    }

    public void setTotalDownload(Long totalDownload) {
        this.totalDownload = totalDownload;
    }

    public Long getDownByContent() {
        return downByContent;
    }

    public void setDownByContent(Long downByContent) {
        this.downByContent = downByContent;
    }

    public Long getDownByShare() {
        return downByShare;
    }

    public void setDownByShare(Long downByShare) {
        this.downByShare = downByShare;
    }

    public Long getDownByOther() {
        return downByOther;
    }

    public void setDownByOther(Long downByOther) {
        this.downByOther = downByOther;
    }

    public Long getVisitStoreCount() {
        return visitStoreCount;
    }

    public void setVisitStoreCount(Long visitStoreCount) {
        this.visitStoreCount = visitStoreCount;
    }

    public Long getVisitStoreUser() {
        return visitStoreUser;
    }

    public void setVisitStoreUser(Long visitStoreUser) {
        this.visitStoreUser = visitStoreUser;
    }

    public Long getTotalInstall() {
        return totalInstall;
    }

    public void setTotalInstall(Long totalInstall) {
        this.totalInstall = totalInstall;
    }

    public Long getInstallWithfm() {
        return installWithfm;
    }

    public void setInstallWithfm(Long installWithfm) {
        this.installWithfm = installWithfm;
    }

    public Long getInstallNonfm() {
        return installNonfm;
    }

    public void setInstallNonfm(Long installNonfm) {
        this.installNonfm = installNonfm;
    }

    public  Long getInstallUser(){
        return installUser;
    }

    public void setInstallUser(Long installUser){
        this.installUser=installUser;
    }

    public Long getTakeTimes() {
        return takeTimes;
    }

    public void setTakeTimes(Long takeTimes) {
        this.takeTimes = takeTimes;
    }

    @Override
    public String toString() {

        return ToStringBuilder.reflectionToString(this);
    }
}
