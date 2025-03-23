package com.solutions.sales.exceptions;


import lombok.Getter;

@Getter
public abstract class AppException extends RuntimeException {
    private final int code;

    protected AppException(String message, int code) {
        super(message);
        this.code = code;
    }

    protected AppException(String message, int code, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}
