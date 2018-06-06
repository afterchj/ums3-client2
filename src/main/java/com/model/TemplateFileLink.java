package com.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-5-19
 * Time: 下午3:03
 * To change this template use File | Settings | File Templates.
 */
//@Entity
//@Table(name="f_file_template")
public class TemplateFileLink extends IdEntity{
    private ThemeFile theme;
    private Template template;
    private Long sort;

//    @ManyToOne
//    @JoinColumn(name="theme_id")
//    @OrderBy("sort asc")
    public ThemeFile getTheme() {
        return theme;
    }

    public void setTheme(ThemeFile theme) {
        this.theme = theme;
    }

//    @ManyToOne
//    @JoinColumn(name="template_id")
    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
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
