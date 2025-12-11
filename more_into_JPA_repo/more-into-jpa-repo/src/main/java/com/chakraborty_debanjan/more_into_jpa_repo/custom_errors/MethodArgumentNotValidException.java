package com.chakraborty_debanjan.more_into_jpa_repo.custom_errors;

public class MethodArgumentNotValidException extends RuntimeException{
    public MethodArgumentNotValidException(String message) {
        super(message);
    }
}
