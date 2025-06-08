package com.clothashe.clotashe_backend.exception;

import com.clothashe.clotashe_backend.exception.misc.*;
import com.clothashe.clotashe_backend.exception.error.ApiError;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        logger.warn("Resource not found-: {}", ex.getMessage());
        ApiError error = new ApiError(
                "Resource not found",
                HttpStatus.NOT_FOUND.value(),
                request.getRequestURI(),
                "RESOURCE_NOT_FOUND"
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(EntityNotFoundException ex, HttpServletRequest request) {
        logger.warn("Resource not found: {}", ex.getMessage());
        ApiError error = new ApiError(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                request.getRequestURI(),
                "ENTITY_NOT_FOUND"
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneral(Exception ex, HttpServletRequest request) {
        logger.error("Unexpected error", ex);
        ApiError error = new ApiError(
                "Unexpected error",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                request.getRequestURI(),
                "INTERNAL_SERVER_ERROR"
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
        logger.warn("Authorization denied, you don't have permission: {}", ex.getMessage());
        ApiError error = new ApiError(
                ex.getMessage(),
                HttpStatus.FORBIDDEN.value(),
                request.getRequestURI(),
                "AUTHORIZATION_DENIED"
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    private ResponseEntity<ApiError> buildErrorResponse(String message, HttpStatus status, String path, String errorCode) {
        ApiError apiError = new ApiError();
        apiError.setMessage(message);
        apiError.setStatus(status.value());
        apiError.setPath(path);
        apiError.setErrorCode(errorCode);
        return new ResponseEntity<>(apiError, status);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiError> handleApiException(ApiException ex, HttpServletRequest request) {
        logger.warn("API exception: {}", ex.getMessage());
        return buildErrorResponse(
                ex.getMessage(),
                ex.getHttpStatus(),
                request.getRequestURI(),
                ex.getErrorCode()
        );
    }

}