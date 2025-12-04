package com.debanjan.Spring_JPA.utils;

import com.debanjan.Spring_JPA.exceptions.BadRequestException;
import com.debanjan.Spring_JPA.exceptions.StudentNotFoundException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;
import org.springframework.transaction.TransactionSystemException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 404 – student not found
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ApiResponse> handleStudentNotFound(StudentNotFoundException ex) {
        log.info("Student not found: {}", ex.getMessage());

        ApiErrorResponse body = ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .debugMessage(ex.getClass().getSimpleName() + " - " + "Student with the given ID not found")
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .traceId(UUID.randomUUID().toString())
                .build();

        ApiResponse response = ApiResponse.builder()
                .error(body)
                .data(null)
                .timestamp(body.getTimestamp())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }


    // 400 – bad request / invalid field / invalid format
    // 400 – bad request (custom)
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<?>> handleBadRequest(BadRequestException ex) {
        log.warn("Bad request: {}", ex.getMessage());

        ApiErrorResponse errorBody = ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .debugMessage(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .traceId(UUID.randomUUID().toString())
                .build();

        ApiResponse<?> response = ApiResponse.builder()
                .data(null)
                .error(errorBody)
                .timestamp(errorBody.getTimestamp())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    // 400 – bean validation errors (@Valid on @RequestBody)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidation(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .collect(Collectors.toList());

        ApiErrorResponse errorBody = ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Validation failed")
                .debugMessage("Validation errors: " + errors)
                .errors(errors)
                .traceId(UUID.randomUUID().toString())
                .build();

        ApiResponse<?> response = ApiResponse.builder()
                .data(null)
                .error(errorBody)
                .timestamp(errorBody.getTimestamp())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    // 400 – constraint violations (e.g. @Min, @Max on entity or params)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<?>> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> errors = ex.getConstraintViolations().stream()
                .map(cv -> cv.getPropertyPath() + ": " + cv.getMessage())
                .toList();

        ApiErrorResponse errorBody = ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Validation failed")
                .debugMessage(ex.getClass().getSimpleName() + " - Age must be between 5 and 20")
                .errors(errors)
                .traceId(UUID.randomUUID().toString())
                .build();

        ApiResponse<?> response = ApiResponse.builder()
                .data(null)
                .error(errorBody)
                .timestamp(errorBody.getTimestamp())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<ApiResponse<?>> handleTransaction(TransactionSystemException ex) {
        Throwable root = ex.getRootCause();
        if (root instanceof ConstraintViolationException cve) {
            return handleConstraintViolation(cve);   // reuse above logic (already wraps in ApiResponse)
        }

        ApiErrorResponse errorBody = ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message("An unexpected error occurred")
                .debugMessage(ex.getMessage())
                .traceId(UUID.randomUUID().toString())
                .build();

        ApiResponse<?> response = ApiResponse.builder()
                .data(null)
                .error(errorBody)
                .timestamp(errorBody.getTimestamp())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    // 500 – everything else (real bugs)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleAll(Exception ex) {
        log.error("Unhandled exception type: {}", ex.getClass().getName(), ex);

        ApiErrorResponse errorBody = ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message("An unexpected error occurred")
                .debugMessage(ex.getMessage())
                .traceId(UUID.randomUUID().toString())
                .build();

        ApiResponse<?> response = ApiResponse.builder()
                .data(null)
                .error(errorBody)
                .timestamp(errorBody.getTimestamp())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
