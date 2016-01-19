package com.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.URL;

public class ThemeThirdURL extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String cmURL; //移动
	private String cuURL; //联通
	private String ctURL; //电信

	private ThemeFile theme;

	@URL
	public String getCmURL() {
		return cmURL;
	}

	public void setCmURL(String cmURL) {
		this.cmURL = cmURL;
	}

	@URL
	public String getCuURL() {
		return cuURL;
	}

	public void setCuURL(String cuURL) {
		this.cuURL = cuURL;
	}

	@URL
	public String getCtURL() {
		return ctURL;
	}

	public void setCtURL(String ctURL) {
		this.ctURL = ctURL;
	}

	public ThemeFile getTheme() {
		return theme;
	}

	public void setTheme(ThemeFile theme) {
		this.theme = theme;
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this);
	}
}
