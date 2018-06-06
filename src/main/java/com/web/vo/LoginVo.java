package com.web.vo;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

public class LoginVo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	// 登陆名称
	private String loginName;
	// 备注
	private String tip;
	// 昵称
	private String nickname;
	private String type;
	// 1. 允许评论 0. 禁止评论
	private Integer allowComment;
	// 1. 允许发帖 0. 禁止发帖
	private Integer allowPost;
	// 性别
	private Integer gender;
	// 省份
	private String prov;
	// 生日
	private String birthday;
	private String qq;
	// 真实姓名
	private String realName;
	private String imei;
	private String imsi;
	private String email;
	// 手机号码
	private String mobile;
	// client-type
	private String ct;
	private String resolution;
	private String model;
	private String fm;
	private String netEnv;
	private String operators;
	private Long visitor;
	// 用户头像
	private String pic;
	private String createDate;
	// 客户端版本
	private String clientVersion;
	private String pkg = "mem";
	private String clazz = "login";

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getAllowComment() {
		return allowComment;
	}

	public void setAllowComment(Integer allowComment) {
		this.allowComment = allowComment;
	}

	public Integer getAllowPost() {
		return allowPost;
	}

	public void setAllowPost(Integer allowPost) {
		this.allowPost = allowPost;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCt() {
		return ct;
	}

	public void setCt(String ct) {
		this.ct = ct;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getFm() {
		return fm;
	}

	public void setFm(String fm) {
		this.fm = fm;
	}

	public String getNetEnv() {
		return netEnv;
	}

	public void setNetEnv(String netEnv) {
		this.netEnv = netEnv;
	}

	public String getOperators() {
		return operators;
	}

	public void setOperators(String operators) {
		this.operators = operators;
	}

	public Long getVisitor() {
		return visitor;
	}

	public void setVisitor(Long visitor) {
		this.visitor = visitor;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public static LoginVo parseFrom(String data)
			throws UnsupportedEncodingException {
		return JSON.parseObject(data, LoginVo.class);
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public boolean hasId() {
		return StringUtils.isNotBlank(loginName)
				&& StringUtils.isNotBlank(type);
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

	public String getClientVersion() {
		return clientVersion;
	}

	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}

	public String getPkg() {
		return pkg;
	}

	public void setPkg(String pkg) {
		this.pkg = pkg;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	@Override
	public String toString() {
		return String.format("{\"id\":\"%s\",\"loginName\":\"%s\",\"type\":\"%s\",\"pic\":\"%s\",\"visitor\":%d,\"nickname\":\"%s\"}", this.id, this.loginName, this.type, this.pic, this.visitor, this.nickname);
	}
	
	public static void main(String[] args) {
		LoginVo vo = new LoginVo();
		vo.setNickname("nickname");
		vo.setId("id");
		vo.setLoginName("loginName");
		System.out.println(com.alibaba.fastjson.JSONObject.toJSONString(vo));
	}

}
