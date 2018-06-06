package com.web.vo;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

public class TopicVo implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String value;
	private String description;
	private String icon;
	private Long sort;
	private List<TopicFileLinkVo> topicFileLinkList = Lists.newArrayList();
	private String editDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public List<TopicFileLinkVo> getTopicFileLinkList() {
		return topicFileLinkList;
	}

	public void setTopicFileLinkList(List<TopicFileLinkVo> topicFileLinkList) {
		this.topicFileLinkList = topicFileLinkList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public String getEditDate() {
		return editDate;
	}

	public void setEditDate(String editDate) {
		this.editDate = editDate;
	}

}
