package com.model;


import java.io.Serializable;

/**
 * Created by nannan.li on 2018/7/11.
 */
public class AppUser implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String loginName;
    private String nickname;
    private String descr;
    private String serialno;
    private TpadUser tpadUser;
    private Application app;
    private String icon;
    private Integer gender;
    private Integer prov;
    private Integer city;
    private String qqProv;
    private String qqCity;
    private String qq;
    private Integer status;
    private Integer mode;
    private Integer mode2;
    private String token;
    private String master;
    private String grandMaster;
    private String type;
    private Integer rank;
    private String qqNickname;
    private String communityUid;
    private boolean communityLoginSuc;

    public AppUser() {
    }

    public String getId() {
        return this.id;
    }

    public String getMaster() {
        return this.master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getGrandMaster() {
        return this.grandMaster;
    }

    public void setGrandMaster(String grandMaster) {
        this.grandMaster = grandMaster;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getRank() {
        return this.rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSerialno() {
        return this.serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno;
    }

    public TpadUser getTpadUser() {
        return this.tpadUser;
    }

    public void setTpadUser(TpadUser tpadUser) {
        this.tpadUser = tpadUser;
    }

    public Application getApp() {
        return this.app;
    }

    public void setApp(Application app) {
        this.app = app;
    }

    public String getDescr() {
        return this.descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getMode() {
        return this.mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public Integer getMode2() {
        return this.mode2;
    }

    public void setMode2(Integer mode2) {
        this.mode2 = mode2;
    }

    public Integer getGender() {
        return this.gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getQq() {
        return this.qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getQqNickname() {
        return this.qqNickname;
    }

    public void setQqNickname(String qqNickname) {
        this.qqNickname = qqNickname;
    }

    public Integer getProv() {
        return this.prov;
    }

    public void setProv(Integer prov) {
        this.prov = prov;
    }

    public Integer getCity() {
        return this.city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public String getQqProv() {
        return this.qqProv;
    }

    public void setQqProv(String qqProv) {
        this.qqProv = qqProv;
    }

    public String getQqCity() {
        return this.qqCity;
    }

    public void setQqCity(String qqCity) {
        this.qqCity = qqCity;
    }

    public String getCommunityUid() {
        return this.communityUid;
    }

    public void setCommunityUid(String communityUid) {
        this.communityUid = communityUid;
    }

    public boolean isCommunityLoginSuc() {
        return this.communityLoginSuc;
    }

    public void setCommunityLoginSuc(boolean communityLoginSuc) {
        this.communityLoginSuc = communityLoginSuc;
    }
}