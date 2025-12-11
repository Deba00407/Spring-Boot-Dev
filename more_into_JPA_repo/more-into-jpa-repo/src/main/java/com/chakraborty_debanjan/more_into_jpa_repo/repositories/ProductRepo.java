package com.chakraborty_debanjan.more_into_jpa_repo.repositories;

import com.chakraborty_debanjan.more_into_jpa_repo.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity, UUID>{
    Optional<ProductEntity> findBySku(String sku);
}
