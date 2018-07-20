package com.web.vo;

/**
 * Created by nannan.li on 2018/7/19.
 */
public enum MobileVerifyType {

    REGISTER("register"),
    UPDATE("update");

    private String value;

    private MobileVerifyType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
