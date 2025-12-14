package com.debanjan.e_commerce.utils.responses;

import com.debanjan.e_commerce.utils.custom_enums.ErrorCodes;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse {
    private String message;
    private HttpStatus status;
    private String path;
    private Map<String, String> errors;
    private ErrorCodes errorCode;
    private Instant timestamp;
}
