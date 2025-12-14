package com.debanjan.e_commerce.utils.custom_annotations.validators;

import com.debanjan.e_commerce.DTO.ProductUpdateDTO;
import com.debanjan.e_commerce.utils.custom_annotations.AtLeastOneFieldPresent;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AtLeastOneFieldPresentValidator implements ConstraintValidator<AtLeastOneFieldPresent, ProductUpdateDTO> {
    @Override
    public boolean isValid(ProductUpdateDTO dto, ConstraintValidatorContext constraintValidatorContext) {
        return dto.getProductName() != null
                || dto.getProductDescription() != null
                || dto.getProductCoverImage() != null
                || dto.getProductPrice() != null
                || dto.getProductCategory() != null
                || dto.getProductStock() != null;
    }
}
