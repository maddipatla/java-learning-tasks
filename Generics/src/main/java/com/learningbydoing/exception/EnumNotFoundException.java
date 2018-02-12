package com.learningbydoing.exception;

/**
 * @author Maddipatla Chandra Babu
 * @date 12-Feb-2018
 */
public class EnumNotFoundException extends RuntimeException {

    public EnumNotFoundException(String message) {
        super(message);
    }
}
