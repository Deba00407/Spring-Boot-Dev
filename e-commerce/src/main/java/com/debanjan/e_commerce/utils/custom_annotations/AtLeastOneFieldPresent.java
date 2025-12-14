package com.debanjan.e_commerce.utils.custom_annotations;

import com.debanjan.e_commerce.utils.custom_annotations.validators.AtLeastOneFieldPresentValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = AtLeastOneFieldPresentValidator.class
)
public @interface AtLeastOneFieldPresent {
    String message() default "At least one field must be provided for update";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
