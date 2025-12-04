package com.debanjan.Spring_JPA.utils;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Builder
public class ApiResponse<T>{
    private T data;
    private ApiErrorResponse error;
    private LocalDateTime timestamp;
}
