package com.web.vo;

import java.io.Serializable;

/**
 * 物流信息
 * @author xuwei
 *
 */
public class Logistics implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String company;
	private String serialno;

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

}
