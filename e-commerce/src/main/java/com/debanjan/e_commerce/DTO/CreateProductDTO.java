package com.debanjan.e_commerce.DTO;

import com.debanjan.e_commerce.utils.custom_enums.Product_Category;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductDTO {

    @NotBlank(message = "Product name cannot be blank.")
    @Size(min = 3, max = 255)
    private String productName;

    @NotBlank(message = "Product description cannot be blank.")
    @Size(max = 255)
    private String productDescription;

    @NotBlank(message = "Product cover image cannot be blank.")
    @URL(protocol = "https", message = "Product cover image must be a valid HTTPS URL.")
    private String productCoverImage;

    @NotNull(message = "Product price is required.")
    @Positive(message = "Product price must be greater than zero.")
    private BigDecimal productPrice;

    @NotNull(message = "Product stock is required.")
    @Positive(message = "Product stock cannot be greater than 0.")
    private Integer productStock;

    @NotNull(message = "Product category is required.")
    private Product_Category productCategory;
}

