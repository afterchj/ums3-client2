package com.model;

/**
 * Created by nannan.li on 2018/7/11.
 */
public class ThirdLogin {
    private static final long serialVersionUID = 1L;
    private String id;
    private String qq;
    private String qq_nickname;
    private String qq_image_url;
    private String wx;
    private String wx_nickname;
    private String wx_image_url;

    public ThirdLogin() {
    }

    public ThirdLogin(String id, String qq, String qq_nickname, String
            qq_image_url, String wx, String wx_nickname, String wx_image_url) {
        this.id = id;
        this.qq = qq;
        this.qq_nickname = qq_nickname;
        this.qq_image_url = qq_image_url;
        this.wx = wx;
        this.wx_nickname = wx_nickname;
        this.wx_image_url = wx_image_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getQq_nickname() {
        return qq_nickname;
    }

    public void setQq_nickname(String qq_nickname) {
        this.qq_nickname = qq_nickname;
    }

    public String getQq_image_url() {
        return qq_image_url;
    }

    public void setQq_image_url(String qq_image_url) {
        this.qq_image_url = qq_image_url;
    }

    public String getWx() {
        return wx;
    }

    public void setWx(String wx) {
        this.wx = wx;
    }

    public String getWx_nickname() {
        return wx_nickname;
    }

    public void setWx_nickname(String wx_nickname) {
        this.wx_nickname = wx_nickname;
    }

    public String getWx_image_url() {
        return wx_image_url;
    }

    public void setWx_image_url(String wx_image_url) {
        this.wx_image_url = wx_image_url;
    }
}
