package com.exception;

public class FileNotCopySucException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FileNotCopySucException() {
		super();
	}

	public FileNotCopySucException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FileNotCopySucException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileNotCopySucException(String message) {
		super(message);
	}

	public FileNotCopySucException(Throwable cause) {
		super(cause);
	}

}
