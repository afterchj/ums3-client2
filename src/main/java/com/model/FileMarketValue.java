package com.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class FileMarketValue extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String keyName;
	private String keyValue;
	private ThemeFile theme;
	private Market market;

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public ThemeFile getTheme() {
		return theme;
	}

	public void setTheme(ThemeFile theme) {
		this.theme = theme;
	}

	public Market getMarket() {
		return market;
	}

	public void setMarket(Market market) {
		this.market = market;
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this);
	}
}
