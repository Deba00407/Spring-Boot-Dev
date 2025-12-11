package com.chakraborty_debanjan.more_into_jpa_repo.repositories;

import com.chakraborty_debanjan.more_into_jpa_repo.utils.ApiErrorResponse;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorRepo extends JpaRepository<@NonNull ApiErrorResponse, @NonNull Long>{
}
