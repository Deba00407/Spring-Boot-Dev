package com.debanjan.e_commerce.DTO;

import com.debanjan.e_commerce.utils.Product_Category;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {
    private Long id;
    private String productCode;
    private String productName;
    private String productDescription;
    private String productCoverImage;
    private BigDecimal productPrice;
    private Product_Category productCategory;
    private Integer productStock;
}
