package com.web.vo;

import java.io.Serializable;

import com.model.Store;

public class FileStoreInfoVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String title;
	private String shortDescription;
	private String longDescription;
	private String author;
	private Long price;
	private String language;
	private Long fiId;
	private ThemeFileVo theme;
	private Store store;

	private Long offset;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Long getFiId() {
		return fiId;
	}

	public void setFiId(Long fiId) {
		this.fiId = fiId;
	}

	public ThemeFileVo getTheme() {
		return theme;
	}

	public void setTheme(ThemeFileVo theme) {
		this.theme = theme;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Long getOffset() {
		return offset;
	}

	public void setOffset(Long offset) {
		this.offset = offset;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileStoreInfoVo other = (FileStoreInfoVo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
