package com.web.vo;

public class Broadcast {

	private String name;
	private String type;
	private Integer price;
	private String code;

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public Integer getPrice() {
		return price;
	}

	public String getCode() {
		return code;
	}

	public Broadcast(String name, String type, Integer price, String code) {
		super();
		this.name = name;
		this.type = type;
		this.price = price;
		this.code = code;
	}

}
