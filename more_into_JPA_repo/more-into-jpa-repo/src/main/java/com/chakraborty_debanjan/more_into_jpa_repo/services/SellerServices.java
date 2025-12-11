package com.chakraborty_debanjan.more_into_jpa_repo.services;

import com.chakraborty_debanjan.more_into_jpa_repo.entities.SellerEntity;
import org.springframework.stereotype.Service;

interface SellerServicesInterface {
    void register_new_seller(SellerEntity new_seller);
    SellerEntity get_seller_by_id(Long id);
    SellerEntity get_seller_by_sku(String sku);
    void update_seller(SellerEntity updated_seller);
    void delete_seller(Long id);
}

@Service
public class SellerServices implements SellerServicesInterface {

    @Override
    public void register_new_seller(SellerEntity new_seller) {
        
    }

    @Override
    public SellerEntity get_seller_by_id(Long id) {
        return null;
    }

    @Override
    public SellerEntity get_seller_by_sku(String sku) {
        return null;
    }

    @Override
    public void update_seller(SellerEntity updated_seller) {

    }

    @Override
    public void delete_seller(Long id) {

    }
}
