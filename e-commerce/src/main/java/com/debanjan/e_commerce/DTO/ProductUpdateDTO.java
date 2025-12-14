package com.debanjan.e_commerce.DTO;

import com.debanjan.e_commerce.utils.custom_enums.Product_Category;
import com.debanjan.e_commerce.utils.custom_annotations.AtLeastOneFieldPresent;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@AtLeastOneFieldPresent(message = "At least one update field must be present.")
public class ProductUpdateDTO {
    @Size(min = 3, max = 255)
    private String productName;

    @Size(max = 255)
    private String productDescription;

    @URL
    @Pattern(
            regexp = "/[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)/ig",
            message = "URL entered is invalid."
    )
    private String productCoverImage;

    @Positive
    private BigDecimal productPrice;

    @Enumerated(EnumType.STRING)
    private Product_Category productCategory;

    @Positive
    private Integer productStock;
}
