package com.debanjan.e_commerce.custom_exceptions;

public class ViolationOfDBConstraintsException extends RuntimeException{
    public ViolationOfDBConstraintsException(String message) {
        super(message);
    }
}
