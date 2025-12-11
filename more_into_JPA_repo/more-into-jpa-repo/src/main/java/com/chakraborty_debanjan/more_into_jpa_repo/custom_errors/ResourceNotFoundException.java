package com.chakraborty_debanjan.more_into_jpa_repo.custom_errors;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
