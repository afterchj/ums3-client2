package com.web.vo;

import java.io.Serializable;
import java.util.Date;

//1	id	string	32	no	订单名称
//2	item	string	32	no	购买物品id
//3	createDate	date		no	订单生成时间
//4	price	int		no	订单金额
//5	status	int		no	订单状态 0 处理中 1. 成功 2. 失败 
//6	name	string	50	no	购买物品名称
//7	fullName	string	50	yes	收货人名称
//8	qq	string	32	yes	QQ号码
//9	mobile	string	32	yes	收货人手机号码
//10	address	string	128	yes	收货地址
//11	serialno	string	32	no	订单编号
//12	logistics	json		yes	物流信息（见JSON数据详解m）

/**
 * 订单
 * 
 * @author xuwei
 *
 */
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String goods;
	private Date createDate;
	private Integer price;
	private Integer status;
	private String name;
	private String fullName;
	private String qq;
	private String mobile;
	private String address;
	private String serialno;
	private String logistics;
	private String uid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getLogistics() {
		return logistics;
	}

	public void setLogistics(String logistics) {
		this.logistics = logistics;
	}

}
