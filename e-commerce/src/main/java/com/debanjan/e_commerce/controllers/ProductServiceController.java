package com.debanjan.e_commerce.controllers;

import com.debanjan.e_commerce.DTO.CreateProductDTO;
import com.debanjan.e_commerce.DTO.ProductResponseDTO;
import com.debanjan.e_commerce.DTO.ProductUpdateDTO;
import com.debanjan.e_commerce.services.ProductService;
import com.debanjan.e_commerce.utils.responses.ApiSuccessResponse;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
@Validated
public class ProductServiceController {

    private final ProductService productService;

    public ProductServiceController(ProductService productService) {
        this.productService = productService;
    }

    private ApiSuccessResponse<Object> responseBuilder(String message, Object data){
        return ApiSuccessResponse.builder()
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    private void updateProduct(ProductResponseDTO product, ProductUpdateDTO dto){
        if(dto.getProductPrice() != null) product.setProductPrice(dto.getProductPrice());
        if (dto.getProductName() != null) product.setProductName(dto.getProductName());
        if (dto.getProductDescription() != null) product.setProductDescription(dto.getProductDescription());
        if(dto.getProductCategory() != null) product.setProductCategory(dto.getProductCategory());
        if(dto.getProductStock() != null) product.setProductStock(dto.getProductStock());
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
        return ResponseEntity.ok(
            responseBuilder("Product added successfully", product)
        );
    }

    @GetMapping("")
    public ResponseEntity<ApiSuccessResponse<Object>> getProductById(@RequestParam("id") Long id){
        ProductResponseDTO product = productService.getProductById(id);
        return ResponseEntity.ok(
            responseBuilder("Product fetched successfully", product)
        );
    }

    @GetMapping("/code")
    public ResponseEntity<ApiSuccessResponse<Object>> getProductByProductCode(@RequestParam String code){
        ProductResponseDTO product = productService.getProductByProductCode(code);
        ApiSuccessResponse<Object> response = responseBuilder("Product fetched successfully", product);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiSuccessResponse<Object>> deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.ok(
                responseBuilder(String.format("Product with id: %s deleted successfully", id), null)
        );
    }

    @DeleteMapping("/delete/code/{productCode}")
    public ResponseEntity<ApiSuccessResponse<Object>> deleteProductByCode(@PathVariable String productCode) {
        productService.deleteProductByProductCode(productCode);
        return ResponseEntity.ok(
                responseBuilder(String.format("Product with product code: %s deleted successfully", productCode), null)
        );
    }


    // COMBINED DELETION LOGIC

//    @DeleteMapping("/delete")
//    public ResponseEntity<ApiSuccessResponse<Object>> deleteProduct(@PathParam("id") Long id, @PathParam("code") String code){
//        String msg = "";
//        if(id != null){
//            productService.deleteProductById(id);
//            msg = String.format("Product with id: %d deleted successfully", id);
//        }else if(code != null){
//            productService.deleteProductByProductCode(code);
//            msg = String.format("Product with code: %s deleted successfully", code);
//        }
//
//        ApiSuccessResponse<Object> response = responseBuilder(msg, null);
//        return ResponseEntity.ok(response);
//    }


//    @PutMapping("/update/{id}")
//    public ResponseEntity<ApiSuccessResponse<Object>> updateProductById(@PathVariable Long id, @RequestBody ProductUpdateDTO dto){
//        // find the product by id
//        ProductResponseDTO product = productService.getProductById(id);
//        updateProduct(product, dto);
//        productService.updateProductById(id, dto);
//        ApiSuccessResponse<Object> response = responseBuilder("Product updated successfully", product);
//        return ResponseEntity.ok(response);
//    }
//
//    @PutMapping("/update/code/{code}")
//    public ResponseEntity<ApiSuccessResponse<Object>> updateProductByProductCode(@PathVariable String code, @RequestBody ProductUpdateDTO dto){
//        ProductResponseDTO product = productService.getProductByProductCode(code);
//        updateProduct(product, dto);
//        productService.updateProductByProductCode(code, dto);
//        ApiSuccessResponse<Object> response = responseBuilder("Product updated successfully", product);
//        return ResponseEntity.ok(response);
//    }
}
