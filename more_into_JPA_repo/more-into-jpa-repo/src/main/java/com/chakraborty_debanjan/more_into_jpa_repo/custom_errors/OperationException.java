package com.chakraborty_debanjan.more_into_jpa_repo.custom_errors;

public class OperationException extends RuntimeException{
    public OperationException(String message){
        super(message);
    }
}
