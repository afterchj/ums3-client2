package com.model;

import java.io.Serializable;

/**
 * Created by nannan.li on 2018/7/19.
 */
public class TpadUser implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String info1;
    private String info2;
    private Integer gender;
    private Integer prov;
    private Integer city;
    private String mobile;
    private String qq;
    private Integer birthyear;
    private Integer birthmonth;
    private Integer birthday;
    private String legalName;
    private String address;
    private String serialno;

    public TpadUser() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInfo1() {
        return this.info1;
    }

    public void setInfo1(String info1) {
        this.info1 = info1;
    }

    public String getInfo2() {
        return this.info2;
    }

    public void setInfo2(String info2) {
        this.info2 = info2;
    }

    public Integer getGender() {
        return this.gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
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

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getQq() {
        return this.qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Integer getBirthyear() {
        return this.birthyear;
    }

    public void setBirthyear(Integer birthyear) {
        this.birthyear = birthyear;
    }

    public String getLegalName() {
        return this.legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public Integer getBirthmonth() {
        return this.birthmonth;
    }

    public void setBirthmonth(Integer birthmonth) {
        this.birthmonth = birthmonth;
    }

    public Integer getBirthday() {
        return this.birthday;
    }

    public void setBirthday(Integer birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSerialno() {
        return this.serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno;
    }
}

