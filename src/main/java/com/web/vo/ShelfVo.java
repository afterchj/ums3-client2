package com.web.vo;

import java.io.Serializable;

//1	id	string	32	no	主键ID    uuid
//2	name	string	32		货架名称
//3	index	int	1		货架排名

public class ShelfVo implements Serializable{

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private Integer index;

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

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

}
