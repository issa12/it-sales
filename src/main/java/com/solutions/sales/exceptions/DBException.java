package com.solutions.sales.exceptions;

public class DBException extends AppException {

    private static final long serialVersionUID = 1L;

    public DBException(String message) {
        super(message, 500);
    }

    public DBException(String message, Throwable cause) {
        super(message, 500, cause);
    }

}
