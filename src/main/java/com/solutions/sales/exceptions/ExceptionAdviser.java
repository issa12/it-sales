package com.solutions.sales.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionAdviser {

    @ExceptionHandler(AppException.class)
    @ResponseBody
    public ResponseEntity<String> handleAppException(AppException ex) {
        return new ResponseEntity<>(String.format("Error: %s, Code: [%s] ",  ex.getMessage(), ex.getCode()), HttpStatus.BAD_REQUEST);
    }

    
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<String> handleGenericException(AppException ex) {
        return new ResponseEntity<>("Error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
