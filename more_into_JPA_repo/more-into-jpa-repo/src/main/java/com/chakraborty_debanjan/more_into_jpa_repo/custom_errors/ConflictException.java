package com.chakraborty_debanjan.more_into_jpa_repo.custom_errors;

public class ConflictException extends RuntimeException{
    public ConflictException(String message){
        super(message);
    }
}
