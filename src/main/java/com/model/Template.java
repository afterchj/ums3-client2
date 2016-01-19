package com.model;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.common.collect.Lists;

//@Entity
//@Table(name="f_template")
public class Template extends IdEntity {
    private String name;
    private String value;
    private String description;
    private String icon;
    private Long sort;
    private List<TemplateFileLink> templateFileLinkList = Lists.newArrayList();


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

//    @OneToMany(mappedBy = "template",fetch = FetchType.LAZY,cascade = {CascadeType.REMOVE},orphanRemoval = true)
    public List<TemplateFileLink> getTemplateFileLinkList() {
        return templateFileLinkList;
    }

    public void setTemplateFileLinkList(List<TemplateFileLink> templateFileLinkList) {
        this.templateFileLinkList = templateFileLinkList;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
