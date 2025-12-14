package com.debanjan.e_commerce;

import com.debanjan.e_commerce.DTO.CreateProductDTO;
import com.debanjan.e_commerce.DTO.ProductResponseDTO;
import com.debanjan.e_commerce.DTO.ProductUpdateDTO;
import com.debanjan.e_commerce.custom_exceptions.BadRequestException;
import com.debanjan.e_commerce.custom_exceptions.ResourceNotFoundException;
import com.debanjan.e_commerce.services.ProductService;
import com.debanjan.e_commerce.utils.Product_Category;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ProductServiceIntegrationTest {

    @Autowired
    private ProductService productService;

    private CreateProductDTO invalidCoverImageProduct() {
        return CreateProductDTO.builder()
                .productName("Invalid URL Product")
                .productStock(10)
                .productCategory(Product_Category.ELECTRONICS)
                .productPrice(new BigDecimal("99.99"))
                .productCoverImage("invalid-url")
                .build();
    }

    // ---------- Failure Scenarios ----------

    @Test
    void should_fail_when_cover_image_url_is_invalid() {
        assertThrows(
                BadRequestException.class,
                () -> productService.saveProduct(invalidCoverImageProduct())
        );
    }

    @Test
    void should_return_empty_list_when_no_products_exist() {
        List<ProductResponseDTO> products = productService.getAllProducts();

        assertNotNull(products);
        assertTrue(products.isEmpty());
    }

    @Test
    void should_fail_when_product_not_found_by_id() {
        assertThrows(
                ResourceNotFoundException.class,
                () -> productService.getProductById(999999L)
        );
    }

    @Test
    void should_fail_when_product_not_found_by_product_code() {
        assertThrows(
                ResourceNotFoundException.class,
                () -> productService.getProductByProductCode("INVALID-CODE")
        );
    }

    @Test
    void should_fail_delete_when_product_code_is_invalid() {
        assertThrows(
                ResourceNotFoundException.class,
                () -> productService.deleteProductByProductCode("INVALID-CODE")
        );
    }

    @Test
    void should_fail_delete_when_product_id_not_found() {
        assertThrows(
                ResourceNotFoundException.class,
                () -> productService.deleteProductById(Long.MAX_VALUE)
        );
    }

    @Test
    void should_fail_update_when_product_code_not_found() {
        ProductUpdateDTO updateDTO = ProductUpdateDTO.builder()
                .productPrice(new BigDecimal("2499.99"))
                .productStock(1000)
                .build();

        assertThrows(
                ResourceNotFoundException.class,
                () -> productService.updateProductByProductCode("INVALID-CODE", updateDTO)
        );
    }

    @Test
    void should_fail_update_when_product_id_not_found() {
        ProductUpdateDTO updateDTO = ProductUpdateDTO.builder()
                .productPrice(new BigDecimal("2499.99"))
                .productStock(1000)
                .build();

        assertThrows(
                ResourceNotFoundException.class,
                () -> productService.updateProductById(Long.MAX_VALUE, updateDTO)
        );
    }
}

