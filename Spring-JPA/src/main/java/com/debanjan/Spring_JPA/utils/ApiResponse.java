package com.debanjan.Spring_JPA.utils;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Builder
public class ApiResponse<T>{
    private T data;
    private ApiErrorResponse error;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}
