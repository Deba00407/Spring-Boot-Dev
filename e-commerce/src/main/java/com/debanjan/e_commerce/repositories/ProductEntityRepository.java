package com.debanjan.e_commerce.repositories;

import com.debanjan.e_commerce.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductEntityRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByProductCode(String productCode);

    void deleteByProductCode(String productCode);

    boolean existsByProductCode(String productCode);
}