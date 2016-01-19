package com.model;


public class City extends LongIDEntity{
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String postCode;
	private Province province;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}
}
