package com.chakraborty_debanjan.more_into_jpa_repo;

import com.chakraborty_debanjan.more_into_jpa_repo.dtos.ProductDTO;
import com.chakraborty_debanjan.more_into_jpa_repo.services.ProductServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;


@SpringBootTest
@ActiveProfiles("dev")
public class ProductServicesTest {
    @Autowired
    private ProductServices productServices;

    @Test
    public void getAllProducts(){
        List<ProductDTO> products = productServices.getAllProducts();
        System.out.println("Products fetched successfully");
        products.forEach(System.out::println);
    }
}
