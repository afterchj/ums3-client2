package com.web.vo;

import java.io.Serializable;


public class TopicFileLinkVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private ThemeFileVo theme;
	private TopicVo topic;
	private Long sort;

	public ThemeFileVo getTheme() {
		return theme;
	}

	public void setTheme(ThemeFileVo theme) {
		this.theme = theme;
	}

	public TopicVo getTopic() {
		return topic;
	}

	public void setTopic(TopicVo topic) {
		this.topic = topic;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
}
