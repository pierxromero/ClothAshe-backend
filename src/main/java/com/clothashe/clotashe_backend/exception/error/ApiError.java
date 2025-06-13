package com.clothashe.clotashe_backend.exception.error;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@Schema(
        name = "ApiError",
        description = "Standard structure to represent errors in API responses"
)
public class ApiError {

    @Schema(
            description = "User-friendly error message",
            example = "Resource not found",
            required = true
    )
    private String message;

    @Schema(
            description = "HTTP status code corresponding to the error",
            example = "404",
            required = true
    )
    private int status;

    @Schema(
            description = "Internal code to identify the error type on the frontend",
            example = "NOT_FOUND",
            required = true
    )
    private String errorCode;

    @Schema(
            description = "Endpoint path where the error occurred",
            example = "/api/users/123",
            required = true
    )
    private String path;

    @Schema(
            description = "Date and time of the error in ISO 8601 format",
            example = "2025-06-08T16:00:00",
            required = true
    )
    private String timestamp;

    public ApiError(String message, int status, String path, String errorCode) {
        this.message = message;
        this.status = status;
        this.path = path;
        this.errorCode = errorCode;
        this.timestamp = LocalDateTime.now().toString();
    }

    public ApiError() {
    }
}