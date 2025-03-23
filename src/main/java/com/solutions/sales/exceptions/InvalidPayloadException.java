package com.solutions.sales.exceptions;

public class InvalidPayloadException extends AppException {
    private static final long serialVersionUID = 1L;
    public InvalidPayloadException(String message) {
        super(message, 600);
    }
    public InvalidPayloadException(String message, Throwable cause) {
        super(message, 600, cause);
    }

}
