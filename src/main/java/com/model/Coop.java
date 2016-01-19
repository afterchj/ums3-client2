package com.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: ken.cui
 * Date: 13-6-6
 * Time: 上午11:11
 */
public class Coop extends IdEntity {
	private static final long serialVersionUID = 1L;
    private String name;
    private String value;
    private String channel;

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

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
