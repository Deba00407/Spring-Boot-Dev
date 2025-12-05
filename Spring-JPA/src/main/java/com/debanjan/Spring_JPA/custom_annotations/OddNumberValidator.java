package com.debanjan.Spring_JPA.custom_annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OddNumberValidator implements ConstraintValidator<OddNumber, Integer> {

    @Override
    public void initialize(OddNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        return integer != null && integer % 2 != 0;
    }
}
