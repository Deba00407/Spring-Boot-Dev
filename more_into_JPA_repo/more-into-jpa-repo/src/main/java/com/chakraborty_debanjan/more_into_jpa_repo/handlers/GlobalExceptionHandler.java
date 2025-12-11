package com.chakraborty_debanjan.more_into_jpa_repo.handlers;

import com.chakraborty_debanjan.more_into_jpa_repo.custom_errors.BadRequestException;
import com.chakraborty_debanjan.more_into_jpa_repo.custom_errors.MethodArgumentNotValidException;
import com.chakraborty_debanjan.more_into_jpa_repo.custom_errors.ResourceNotFoundException;
import com.chakraborty_debanjan.more_into_jpa_repo.utils.ApiErrorResponse;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // TODO -> Add custom exception handler for custom exceptions

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<@NonNull ApiErrorResponse> handleBadRequest(BadRequestException ex){
        ApiErrorResponse response = ApiErrorResponse
                .builder()
                .error_id(UUID.fromString(ex.getMessage().split(":")[1].trim()))
                .error(ex.getMessage())
                .error_message(ex.getLocalizedMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<@NonNull ApiErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex){
        ApiErrorResponse response = ApiErrorResponse
                .builder()
                .error_id(UUID.fromString(ex.getMessage().split(":")[1].trim()))
                .error(ex.getMessage())
                .error_message(ex.getLocalizedMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<@NonNull ApiErrorResponse> handleResourceNotFound(ResourceNotFoundException ex){
        ApiErrorResponse response = ApiErrorResponse
                .builder()
                .error_id(UUID.fromString(ex.getMessage().split(":")[1].trim()))
                .error(ex.getMessage())
                .error_message(ex.getLocalizedMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<@NonNull ApiErrorResponse> handleAll(Exception ex){
        ApiErrorResponse response = ApiErrorResponse
                .builder()
                .error_id(UUID.fromString(ex.getMessage().split(":")[1].trim()))
                .error(ex.getMessage())
                .error_message(ex.getLocalizedMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
