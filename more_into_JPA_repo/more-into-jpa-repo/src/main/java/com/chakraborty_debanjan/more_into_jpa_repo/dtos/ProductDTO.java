package com.chakraborty_debanjan.more_into_jpa_repo.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Builder
public class ProductDTO {
    private String product_name;

    private String short_description;

    @ToString.Exclude
    private String sku;

    private String long_description;

    private String product_cover_image;

    private List<String> product_images;
}
