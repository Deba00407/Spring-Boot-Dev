package com.debanjan.e_commerce.handlers;

import com.debanjan.e_commerce.custom_exceptions.BadRequestException;
import com.debanjan.e_commerce.custom_exceptions.ResourceNotFoundException;
import com.debanjan.e_commerce.utils.custom_enums.ErrorCodes;
import com.debanjan.e_commerce.utils.responses.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // =========================
    // Bad Request (custom)
    // =========================
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiErrorResponse> handleBadRequest(
            BadRequestException ex,
            HttpServletRequest request) {

//        log.warn("Bad request at {}", request.getRequestURI(), ex);

        return ResponseEntity.badRequest().body(
                ApiErrorResponse.builder()
                        .message("Invalid request")
                        .errorCode(ErrorCodes.INVALID_REQUEST)
                        .status(HttpStatus.BAD_REQUEST)
                        .path(request.getRequestURI())
                        .timestamp(Instant.now())
                        .build()
        );
    }

    // =========================
    // DTO validation (@Valid @RequestBody)
    // =========================
    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(err ->
                        errors.put(err.getField(), err.getDefaultMessage())
                );

        return ResponseEntity.badRequest().body(
                ApiErrorResponse.builder()
                        .message("Validation failed")
                        .errorCode(ErrorCodes.VALIDATION_ERROR)
                        .status(HttpStatus.BAD_REQUEST)
                        .errors(errors)
                        .path(request.getRequestURI())
                        .timestamp(Instant.now())
                        .build()
        );
    }

    // =========================
    // PathVariable / RequestParam validation
    // =========================
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest request) {

        Map<String, String> errors = new HashMap<>();

        ex.getConstraintViolations()
                .forEach(v ->
                        errors.put(v.getPropertyPath().toString(), v.getMessage())
                );

        return ResponseEntity.badRequest().body(
                ApiErrorResponse.builder()
                        .message("Validation failed")
                        .errorCode(ErrorCodes.VALIDATION_ERROR)
                        .status(HttpStatus.BAD_REQUEST)
                        .errors(errors)
                        .path(request.getRequestURI())
                        .timestamp(Instant.now())
                        .build()
        );
    }

    // =========================
    // Resource not found
    // =========================
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiErrorResponse.builder()
                        .message(ex.getMessage())
                        .errorCode(ErrorCodes.RESOURCE_NOT_FOUND)
                        .status(HttpStatus.NOT_FOUND)
                        .path(request.getRequestURI())
                        .timestamp(Instant.now())
                        .build()
        );
    }

    // When passed payload is not readable by Jackson(e.g Wrong product category not in enum)

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleHttpRequestNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request) {
        return ResponseEntity.badRequest().body(
                ApiErrorResponse.builder()
                        .message("Invalid request payload")
                        .errorCode(ErrorCodes.INVALID_REQUEST)
                        .status(HttpStatus.BAD_REQUEST)
                        .path(request.getRequestURI())
                        .timestamp(Instant.now())
                        .build()
        );
    }

    // =========================
    // Fallback (LAST HANDLER)
    // =========================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleAll(
            Exception ex,
            HttpServletRequest request) {

//        log.error("Unhandled exception at {}", request.getRequestURI(), ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiErrorResponse.builder()
                        .message("Internal server error")
                        .errorCode(ErrorCodes.INTERNAL_ERROR)
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .path(request.getRequestURI())
                        .timestamp(Instant.now())
                        .build()
        );
    }
}

