package com.debanjan.Spring_JPA.custom_annotations;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = OddNumberValidator.class)
public @interface OddNumber {
    String message() default "Not a prime number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
