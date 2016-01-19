package com.exception;

import com.tpadsz.exception.ApplicationException;

public class GoodsNotEnoughException extends ApplicationException{

	private static final long serialVersionUID = 1L;

	public GoodsNotEnoughException() {
		super();
	}

	public GoodsNotEnoughException(String message, Throwable cause) {
		super(message, cause);
	}

	public GoodsNotEnoughException(String message) {
		super(message);
	}

	@Override
	public String getCode() {
		return "101";
	}

}
