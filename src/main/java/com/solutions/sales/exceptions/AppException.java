package com.solutions.sales.exceptions;


public abstract class AppException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private final int code;

    public AppException(String message, int code) {
        super(message);
        this.code = code;
    }

    public AppException(String message, int code, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
