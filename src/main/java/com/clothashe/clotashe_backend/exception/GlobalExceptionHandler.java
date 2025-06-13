package com.clothashe.clotashe_backend.exception;

import com.clothashe.clotashe_backend.exception.misc.*;
import com.clothashe.clotashe_backend.exception.error.ApiError;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.List;


@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ApiError> handleMalformedJwtException(MalformedJwtException ex, HttpServletRequest request) {
        logger.warn("Malformed JWT: {}", ex.getMessage());
        ApiError error = new ApiError(
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                request.getRequestURI(),
                "MALFORMED_JWT"
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ApiError> handleLockedException(LockedException ex, HttpServletRequest request) {
        logger.warn("User is locked: {}", ex.getMessage());
        ApiError error = new ApiError(
                ex.getMessage(),
                HttpStatus.FORBIDDEN.value(),
                request.getRequestURI(),
                "USER_LOCKED"
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentials(BadCredentialsException ex, HttpServletRequest request) {
        logger.warn("Bad credentials: {}", ex.getMessage());
        ApiError error = new ApiError(
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                request.getRequestURI(),
                "BAD_CREDENTIALS"
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiError> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpServletRequest request) {

        String paramName = ex.getParameterName();
        String message = String.format("Missing required request parameter: '%s'", paramName);

        ApiError error = new ApiError(
                message,
                HttpStatus.BAD_REQUEST.value(),
                request.getRequestURI(),
                "MISSING_PARAMETER"
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        String paramName = ex.getName();
        String paramValue = ex.getValue() != null ? ex.getValue().toString() : "null";
        String expectedType = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "Unknown";

        String message = String.format("Parameter '%s' with value '%s' could not be converted to type '%s'", paramName, paramValue, expectedType);

        ApiError error = new ApiError(
                message,
                HttpStatus.BAD_REQUEST.value(),
                request.getRequestURI(),
                "METHOD_ARGUMENT_TYPE_MISMATCH"
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request) {
        logger.warn("Malformed or missing request body: {}", ex.getMessage());

        ApiError error = new ApiError(
                "Request body is missing or malformed",
                HttpStatus.BAD_REQUEST.value(),
                request.getRequestURI(),
                "BAD_REQUEST_BODY"
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        logger.warn("Validation error: {}", ex.getMessage());
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList();
        String errorMessage = String.join("; ", errors);
        ApiError error = new ApiError(
                errorMessage,
                HttpStatus.BAD_REQUEST.value(),
                request.getRequestURI(),
                "VALIDATION_ERROR"
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

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
        apiError.setTimestamp(LocalDateTime.now().toString());
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