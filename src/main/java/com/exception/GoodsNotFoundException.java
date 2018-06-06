package com.exception;

import com.tpadsz.exception.ApplicationException;

public class GoodsNotFoundException extends ApplicationException{

	private static final long serialVersionUID = 1L;

	public GoodsNotFoundException() {
		super();
	}

	public GoodsNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public GoodsNotFoundException(String message) {
		super(message);
	}

	@Override
	public String getCode() {
		return "102";
	}

}
