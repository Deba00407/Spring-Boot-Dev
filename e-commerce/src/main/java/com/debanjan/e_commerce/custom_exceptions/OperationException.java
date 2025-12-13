package com.debanjan.e_commerce.custom_exceptions;

public class OperationException extends RuntimeException{
    public OperationException(String message){
        super(message);
    }
}
