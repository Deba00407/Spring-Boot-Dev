package com.debanjan.e_commerce.utils;

import org.apache.commons.validator.routines.UrlValidator;

public class ValidateURL {
    public static boolean isValid(String url){
        UrlValidator urlValidator = new UrlValidator(
                new String[]{"http", "https"},
                UrlValidator.ALLOW_LOCAL_URLS
        );

        return urlValidator.isValid(url);
    }
}
