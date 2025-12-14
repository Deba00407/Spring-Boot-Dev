package com.debanjan.e_commerce.utils.custom_enums;

public enum ErrorCodes {

    // =========================
    // Generic / System Errors
    // =========================
    INTERNAL_ERROR,            // Unexpected server failure
    SERVICE_UNAVAILABLE,       // Downstream service unavailable
    TIMEOUT,                   // Operation timed out

    // =========================
    // Validation Errors
    // =========================
    VALIDATION_ERROR,          // DTO / request validation failed
    INVALID_REQUEST,           // Malformed request payload
    INVALID_ENUM_VALUE,        // Enum value isn't allowed
    MISSING_REQUIRED_FIELD,    // Required field missing

    // =========================
    // Authentication / AuthZ
    // =========================
    UNAUTHORIZED,              // Not authenticated
    FORBIDDEN,                 // Authenticated but not allowed
    TOKEN_EXPIRED,             // Auth token expired
    INVALID_TOKEN,             // Token invalid

    // =========================
    // Resource Errors
    // =========================
    RESOURCE_NOT_FOUND,        // Generic not found
    PRODUCT_NOT_FOUND,         // Domain-specific example
    USER_NOT_FOUND,

    // =========================
    // Conflict / State Errors
    // =========================
    RESOURCE_ALREADY_EXISTS,   // Duplicate resource
    CONFLICT,                  // State conflict (409)
    VERSION_MISMATCH,          // Optimistic locking failure

    // =========================
    // Rate Limiting / Abuse
    // =========================
    RATE_LIMIT_EXCEEDED,       // Too many requests

    // =========================
    // Business Rule Violations
    // =========================
    BUSINESS_RULE_VIOLATION,   // Generic business rule error
    INSUFFICIENT_STOCK,        // Example domain rule
    PAYMENT_FAILED             // Example domain rule
}

