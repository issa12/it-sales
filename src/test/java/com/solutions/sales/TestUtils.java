package com.solutions.sales;

public class TestUtils {

    public static String buildFailMessage(String message, Object expected, Object actual) {
        return message + " Expected: " + expected + " but was: " + actual;
        
    }
}
