package com.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-5-19
 * Time: 下午3:03
 * To change this template use File | Settings | File Templates.
 */
public class TopicFileLink extends IdEntity{
	private static final long serialVersionUID = 1L;
    private ThemeFile theme;
    private Topic topic;
    private Long sort;

    public ThemeFile getTheme() {
        return theme;
    }

    public void setTheme(ThemeFile theme) {
        this.theme = theme;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
