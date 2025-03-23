package com.solutions.sales.exceptions;

public class InvalidValueException extends AppException {

    private static final long serialVersionUID = 1L;

    public InvalidValueException(String message) {
        super(message, 400);
    }

    public InvalidValueException(String message, Throwable cause) {
        super(message, 400, cause);
    }

}
