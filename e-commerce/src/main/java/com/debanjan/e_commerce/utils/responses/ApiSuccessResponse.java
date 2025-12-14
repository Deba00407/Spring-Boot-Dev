package com.debanjan.e_commerce.utils.responses;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiSuccessResponse<T>{
    private T data;
    private String message;
    private Object meta; // for meta data in pagination
    private LocalDateTime timestamp;
}
