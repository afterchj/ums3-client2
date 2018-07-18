package com.model;

/**
 * Created by nannan.li on 2018/7/11.
 */
public class AppUser {

    private static final long serialVersionUID = 1L;
    private String id;
    private String login_name;
    private String nickname;
    private String mobile;
    private String icon;
    private Integer birthyear;
    private Integer birthmonth;
    private Integer birthday;

    public AppUser() {
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getBirthyear() {
        return birthyear;
    }

    public void setBirthyear(Integer birthyear) {
        this.birthyear = birthyear;
    }

    public Integer getBirthmonth() {
        return birthmonth;
    }

    public void setBirthmonth(Integer birthmonth) {
        this.birthmonth = birthmonth;
    }

    public Integer getBirthday() {
        return birthday;
    }

    public void setBirthday(Integer birthday) {
        this.birthday = birthday;
    }
}
