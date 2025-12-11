package com.chakraborty_debanjan.more_into_jpa_repo.custom_errors;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message) {
        super(message);
    }
}
