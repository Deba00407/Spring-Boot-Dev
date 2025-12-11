package com.chakraborty_debanjan.more_into_jpa_repo.repositories;

import com.chakraborty_debanjan.more_into_jpa_repo.entities.SellerEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepo extends JpaRepository<@NonNull SellerEntity, @NonNull Long> {
}
