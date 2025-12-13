package com.chakraborty_debanjan.more_into_jpa_repo.controllers;

import com.chakraborty_debanjan.more_into_jpa_repo.dtos.ProductDTO;
import com.chakraborty_debanjan.more_into_jpa_repo.services.ProductServices;
import com.chakraborty_debanjan.more_into_jpa_repo.utils.ApiSuccessResponse;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

interface ProductControllerInterface{
    ResponseEntity<@NonNull ApiSuccessResponse<Object>> getAllProducts();
    ResponseEntity<@NonNull ApiSuccessResponse<Object>> getProductById(UUID id);
    ApiSuccessResponse<Object> createProduct(Object product);
    ApiSuccessResponse<Object> deleteProduct(String id);
    ApiSuccessResponse<Object> updateProduct(String id, Object product);
    ApiSuccessResponse<Object> searchProduct(String sku);
    ApiSuccessResponse<Object> getProductsBySellerId(Long id);
}

@RestController
@RequestMapping("/products")
public class ProductController implements ProductControllerInterface{

    private final ProductServices productServices;

    public ProductController(ProductServices productServices) {
        this.productServices = productServices;
    }

    @Override
    @GetMapping("")
    public ResponseEntity<@NonNull ApiSuccessResponse<Object>> getAllProducts() {
        List<ProductDTO> products = productServices.getAllProducts();
        ApiSuccessResponse<Object> response = ApiSuccessResponse.builder()
                .response_message("Products fetched successfully")
                .data(products)
                .status(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("/fetch-product")
    public ResponseEntity<@NonNull ApiSuccessResponse<Object>> getProductById(@RequestParam("id") UUID id) {
        ProductDTO product = productServices.getProductById(id);
        ApiSuccessResponse<Object> response = ApiSuccessResponse.builder()
                .response_message("Product fetched successfully")
                .data(product)
                .status(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(response);
    }

    @Override
    public ApiSuccessResponse<Object> createProduct(Object product) {
        return null;
    }

    @Override
    public ApiSuccessResponse<Object> deleteProduct(String id) {
        return null;
    }

    @Override
    public ApiSuccessResponse<Object> updateProduct(String id, Object product) {
        return null;
    }

    @Override
    public ApiSuccessResponse<Object> searchProduct(String sku) {
        return null;
    }

    @Override
    public ApiSuccessResponse<Object> getProductsBySellerId(Long id) {
        return null;
    }
}
