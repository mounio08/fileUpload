package com.uploadfile.exception;

public class StException extends RuntimeException {

	private static final long serialVersionUID = -338112300406596775L;

	public StException(String message) {
        super(message);
    }

    public StException(String message, Throwable cause) {
        super(message, cause);
    }
}
