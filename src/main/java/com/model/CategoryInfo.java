package com.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class CategoryInfo extends CateItem {
	private static final long serialVersionUID = 1L;
	private Category category;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this);
	}
}
