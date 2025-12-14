package com.debanjan.e_commerce.utils.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // include only non null values in the response
public class ApiSuccessResponse<T>{
    private T data;
    private String message;
    private Object meta; // for meta data in pagination
    private LocalDateTime timestamp;
}
