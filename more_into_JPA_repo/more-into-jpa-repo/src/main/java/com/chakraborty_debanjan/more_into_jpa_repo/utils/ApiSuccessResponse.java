package com.chakraborty_debanjan.more_into_jpa_repo.utils;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiSuccessResponse<T>{
    private T data;
    private String response_message;
    private HttpStatus status;
    private LocalDateTime timestamp;
}
