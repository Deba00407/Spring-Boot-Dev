package com.debanjan.e_commerce.controllers;

import com.debanjan.e_commerce.DTO.CreateProductDTO;
import com.debanjan.e_commerce.DTO.ProductResponseDTO;
import com.debanjan.e_commerce.services.ProductService;
import com.debanjan.e_commerce.utils.responses.ApiSuccessResponse;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductServiceController {

    private final ProductService productService;

    public ProductServiceController(ProductService productService) {
        this.productService = productService;
    }

    private ApiSuccessResponse<Object> responseBuilder(String message, Object data){
        return ApiSuccessResponse.builder()
                .success(true)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @GetMapping("/all")
    public ResponseEntity<ApiSuccessResponse<Object>> getAllProducts(){
        List<ProductResponseDTO> products = productService.getAllProducts();
        ApiSuccessResponse<Object> response = responseBuilder("All products fetched successfully", products);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add/new-product")
    public ResponseEntity<ApiSuccessResponse<Object>> addANewProduct(@RequestBody CreateProductDTO dto){
        ProductResponseDTO product = productService.saveProduct(dto);
        ApiSuccessResponse<Object> response = responseBuilder("Product added successfully", product);
        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    public ResponseEntity<ApiSuccessResponse<Object>> getProductById(@PathParam("id") Long id){
        ProductResponseDTO product = productService.getProductById(id);
        ApiSuccessResponse<Object> response = responseBuilder("Product fetched successfully", product);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{code}")
    public ResponseEntity<ApiSuccessResponse<Object>> getProductByProductCode(@PathVariable String code){
        ProductResponseDTO product = productService.getProductByProductCode(code);
        ApiSuccessResponse<Object> response = responseBuilder("Product fetched successfully", product);
        return ResponseEntity.ok(response);
    }


}
