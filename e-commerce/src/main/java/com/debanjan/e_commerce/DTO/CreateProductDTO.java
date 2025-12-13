package com.debanjan.e_commerce.DTO;

import com.debanjan.e_commerce.utils.Product_Category;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductDTO {
    private String productName;
    private String productDescription;
    private String productCoverImage;
    private BigDecimal productPrice;
    private Integer productStock;
    private Product_Category productCategory;
}
