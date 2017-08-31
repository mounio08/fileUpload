package com.uploadfile.exception;

import com.uploadfile.exception.StException;

public class NfException extends StException {

	private static final long serialVersionUID = -5868639002007736553L;

	public NfException(String message) {
        super(message);
    }

    public NfException(String message, Throwable cause) {
        super(message, cause);
    }
}