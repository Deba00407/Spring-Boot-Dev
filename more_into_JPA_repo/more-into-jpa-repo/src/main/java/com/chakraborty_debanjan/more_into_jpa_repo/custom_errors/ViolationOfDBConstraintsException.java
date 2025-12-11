package com.chakraborty_debanjan.more_into_jpa_repo.custom_errors;

public class ViolationOfDBConstraintsException extends RuntimeException{
    public ViolationOfDBConstraintsException(String message) {
        super(message);
    }
}
