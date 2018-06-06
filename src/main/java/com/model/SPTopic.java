package com.model;

import java.util.Date;

public class SPTopic extends LongIDEntity {
    //专题名
    private String name;
    //待使用(预留)
    private String value;
    //专题描述
    private String description;
    //专题显示图url
    private String icon;
    //专题序号
    private Long sort;
    //编辑时间
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

    public Date getEditDate() {
        return editDate;
    }

    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }
}
