package com.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ShelfFileLink extends IdEntity {
	private static final long serialVersionUID = 1L;
	private Shelf shelf;
	private ThemeFile theme;

	private long sort;

	public Shelf getShelf() {
		return shelf;
	}

	public void setShelf(Shelf shelf) {
		this.shelf = shelf;
	}

	public ThemeFile getTheme() {
		return theme;
	}

	public void setTheme(ThemeFile theme) {
		this.theme = theme;
	}

	public long getSort() {
		return sort;
	}

	public void setSort(long sort) {
		this.sort = sort;
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this);
	}
}
