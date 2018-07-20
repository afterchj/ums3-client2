package com.model;

import com.mapper.BeanMapper;
import com.tpadsz.exception.SystemAlgorithmException;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by nannan.li on 2018/7/19.
 */
public class DAppUser implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String loginName;
    private String nickname;
    private String descr;
    private String password;
    private String salt;
    private String serialno;
    // 用户状态， 是否停用
    private Integer status = 1;
    private Date createDate = new Date();
    private DTpadUser tpadUser;
    private DApplication app;
    private Integer isSubmit = 0;
    // 用户是否激活账号（绑定密码）
    private Integer isActivated = 0;
    // 客户端模式 0. 不开启赚钱模式 1.开启赚钱模式
    private Integer mode = 1;
    // 中间页模式 0:不开启中间页 1.开启中间页
    private Integer mode2 = 1;

    // firmware中内容
    private String clientVersion;
    private String imei;
    private String imsi;
    private String fm;
    private String os;
    private String model;
    private String operators;
    private String resolution;
    private String netEnv;
    private String pkg;
    // begin 老板锁屏中新加字段
    private String master;
    private String grandMaster;
    private String type;
    private Integer rank;
    // qq同步
    private String qqNickname;
    private Integer gender;
    private String qqProv;
    private String qqCity;
    private String qq;
    private String icon;
    // umeng 同步
    private String communityUid;
    private int communityFlg;
    // end

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getGrandMaster() {
        return grandMaster;
    }

    public void setGrandMaster(String grandMaster) {
        this.grandMaster = grandMaster;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getSerialno() {
        return serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno;
    }

    public DApplication getApp() {
        return app;
    }

    public void setApp(DApplication app) {
        this.app = app;
    }

    public AppUser convert() throws SystemAlgorithmException {
        AppUser appUser = BeanMapper.map(this, AppUser.class);
        if (1 == this.getCommunityFlg()) {
            appUser.setCommunityLoginSuc(true);
        } else {
            appUser.setCommunityLoginSuc(false);
        }
        try {
            Application app = BeanMapper.map(this.app, Application.class);
            appUser.setApp(app);
            TpadUser tpadUser = BeanMapper.map(this.tpadUser, TpadUser.class);
            appUser.setTpadUser(tpadUser);
        } catch (Exception e) {
            throw new SystemAlgorithmException("bean:DAppUser, method:convert", e);
        }
        return appUser;
    }

    public DTpadUser getTpadUser() {
        return tpadUser;
    }

    public void setTpadUser(DTpadUser tpadUser) {
        this.tpadUser = tpadUser;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Integer getIsSubmit() {
        return isSubmit;
    }

    public void setIsSubmit(Integer isSubmit) {
        this.isSubmit = isSubmit;
    }

    public Integer getIsActivated() {
        return isActivated;
    }

    public void setIsActivated(Integer isActivated) {
        this.isActivated = isActivated;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public Integer getMode2() {
        return mode2;
    }

    public void setMode2(Integer mode2) {
        this.mode2 = mode2;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getFm() {
        return fm;
    }

    public void setFm(String fm) {
        this.fm = fm;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOperators() {
        return operators;
    }

    public void setOperators(String operators) {
        this.operators = operators;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getNetEnv() {
        return netEnv;
    }

    public void setNetEnv(String netEnv) {
        this.netEnv = netEnv;
    }

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getQqNickname() {
        return qqNickname;
    }

    public void setQqNickname(String qqNickname) {
        this.qqNickname = qqNickname;
    }

    public String getQqProv() {
        return qqProv;
    }

    public void setQqProv(String qqProv) {
        this.qqProv = qqProv;
    }

    public String getQqCity() {
        return qqCity;
    }

    public void setQqCity(String qqCity) {
        this.qqCity = qqCity;
    }

    public int getCommunityFlg() {
        return communityFlg;
    }

    public void setCommunityFlg(int communityFlg) {
        this.communityFlg = communityFlg;
    }

    public String getCommunityUid() {
        return communityUid;
    }

    public void setCommunityUid(String communityUid) {
        this.communityUid = communityUid;
    }

}

