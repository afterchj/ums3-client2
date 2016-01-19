package com.model;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.common.collect.Lists;

/**
 * Created with IntelliJ IDEA. User: Administrator Date: 13-5-19 Time: 下午2:28 To
 * change this template use File | Settings | File Templates.
 */
public class Topic extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String name;
	private String value;
	private String description;
	private String icon;
	private Long sort;
	private List<TopicFileLink> topicFileLinkList = Lists.newArrayList();
	private Date editDate;

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

	public List<TopicFileLink> getTopicFileLinkList() {
		return topicFileLinkList;
	}

	public void setTopicFileLinkList(List<TopicFileLink> topicFileLinkList) {
		this.topicFileLinkList = topicFileLinkList;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public Date getEditDate() {
		return editDate;
	}

	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}
}
