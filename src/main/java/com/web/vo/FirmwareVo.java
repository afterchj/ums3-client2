package com.web.vo;

import java.io.Serializable;

//1	uid	用户id， 未登陆时不需要提供	32	必须提供
//2	ct	客户端版本	32	必须提供
//3	imei	imei号	32	NULL
//4	imsi	当前SIM卡的imsi号	32	NULL
//5	fm	渠道fm	32	必须提供
//6	os	操作系统	32	NULL
//7	model	手机机型	255	NULL
//8	carrier	运营商：[CMCC|CUC|CNC]（注：即移动，联通，电信）	4	NULL
//9	res	屏幕分辨率	32	NULL
//10	net	网络类型	32	NULL
//11	pkg	包名	32	NULL

public class FirmwareVo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String uid;
	private String ct;
	private String imei;
	private String imsi;
	private String fm;
	private String os;
	private String model;
	private String carrier;
	private String res;
	private String net;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getCt() {
		return ct;
	}

	public void setCt(String ct) {
		this.ct = ct;
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

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getRes() {
		return res;
	}

	public void setRes(String res) {
		this.res = res;
	}

	public String getNet() {
		return net;
	}

	public void setNet(String net) {
		this.net = net;
	}

}
