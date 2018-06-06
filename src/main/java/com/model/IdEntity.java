package com.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class IdEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	
}
