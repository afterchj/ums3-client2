package com.web.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;

//1	id	string	32	no	主键ID    uuid
//2	name	string	50	no	应用名称
//3	version	string	32	no	应用版本
//4	pkg	string	32	no	包名
//5	limitOfShow	int			任务展示次数限制
//6	download	string	255	no	下载地址
//7	detail	string	255	no	页面详情数据或者网页
//8	price	int		no	客户端显示价格

public class TaskCpaVo implements Serializable{

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String version;
	private String pkg;
	private Integer limitOfShow;
	private String download;
	private String detail;
	private Integer price;
	private String cv;
	private List<Broadcast> broadcast;
	
	public Map<String, String> toJsonMap(){
		Map<String, String> datamap = Maps.newHashMap();
		datamap.put("id", id);
		datamap.put("name", name);
		datamap.put("version", version);
		datamap.put("pkg", pkg);
		datamap.put("limitOfShow", String.valueOf(limitOfShow));
		datamap.put("download", download);
		datamap.put("detail", detail);
		datamap.put("price", String.valueOf(price));
		datamap.put("cv", cv);
		datamap.put("broadcast", JSON.toJSONString(broadcast));
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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPkg() {
		return pkg;
	}

	public void setPkg(String pkg) {
		this.pkg = pkg;
	}

	public Integer getLimitOfShow() {
		return limitOfShow;
	}

	public void setLimitOfShow(Integer limitOfShow) {
		this.limitOfShow = limitOfShow;
	}

	public String getDownload() {
		return download;
	}

	public void setDownload(String download) {
		this.download = download;
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

	public List<Broadcast> getBroadcast() {
		return broadcast;
	}

	public void setBroadcast(List<Broadcast> broadcast) {
		this.broadcast = broadcast;
	}

	public String getCv() {
		return cv;
	}

	public void setCv(String cv) {
		this.cv = cv;
	}

}
