package com.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class PollingPreview extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String name;
	private String addr;
	private String spec;

	private Polling polling;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public Polling getPolling() {
		return polling;
	}

	public void setPolling(Polling polling) {
		this.polling = polling;
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this);
	}
}
