package com.web.vo;

import java.io.Serializable;
import java.util.Map;

import com.google.common.collect.Maps;

//1	id	string	32	no	主键ID    uuid
//2	name	string	50	no	应用名称
//3	limitOfShow	int			任务展示次数限制
//4	detail	string	255	no	页面展示地址
//5	price	int		no	客户端显示价格
//6	dtype	int	1	no	1: webview展示     2. 本地浏览器

public class TaskCpcVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private Integer limitOfShow;
	private String detail;
	private Integer price;
	private Integer dtype;
	
	public Map<String, String> toJsonMap() {
		Map<String, String> datamap = Maps.newHashMap();
		datamap.put("id", id);
		datamap.put("name", name);
		datamap.put("limitOfShow", String.valueOf(limitOfShow));
		datamap.put("detail", detail);
		datamap.put("price", String.valueOf(price));
		datamap.put("dtype", String.valueOf(dtype));
		return datamap;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLimitOfShow() {
		return limitOfShow;
	}

	public void setLimitOfShow(Integer limitOfShow) {
		this.limitOfShow = limitOfShow;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getDtype() {
		return dtype;
	}

	public void setDtype(Integer dtype) {
		this.dtype = dtype;
	}

}
