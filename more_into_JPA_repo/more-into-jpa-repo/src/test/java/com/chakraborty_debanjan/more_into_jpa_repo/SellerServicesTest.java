package com.chakraborty_debanjan.more_into_jpa_repo;


import com.chakraborty_debanjan.more_into_jpa_repo.dtos.SellerDTO;
import com.chakraborty_debanjan.more_into_jpa_repo.services.SellerServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class SellerServicesTest {
    @Autowired
    private SellerServices sellerServices;

    @Test
    public void testGetSellerById(){
        SellerDTO se = sellerServices.getSellerById(5L);
        System.out.println(se);
    }

    @Test
    public void testDeleteSeller(){
        sellerServices.deleteSeller(1L);
        System.out.println("Seller deleted successfully");
    }

    @Test
    public void testGetAllSellers(){
        sellerServices.getAllSellers().forEach(System.out::println);
    }

    @Test
    public void testRegisterNewSeller(){
        SellerDTO seller = new SellerDTO("newly-added-seller");
        sellerServices.registerNewSeller(seller);
        System.out.println("Seller added successfully");
    }

}
