package com.chakraborty_debanjan.more_into_jpa_repo.utils;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorResponse {

    private UUID error_id;

    private String error;

    private String error_message;

    private LocalDateTime timestamp;
}
