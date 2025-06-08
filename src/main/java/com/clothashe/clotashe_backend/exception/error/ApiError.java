package com.clothashe.clotashe_backend.exception.error;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
@Schema(
        name        = "ApiError",
        description = "Structure for API errors",
        example = """
  {
    "message": "Resource not founds",
    "status": 404,
    "errorCode": "NOT_FOUND",
    "path": "/api/users/123",
    "timestamp": "2025-06-08T16:00:00"
  }
  """
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {

    @Schema(description = "Error message to display to the user")
    private String message;

    @Schema(description = "HTTP status code of the error")
    private int status;

    @Schema(description = "Internal code to distinguish errors on the frontend")
    private String errorCode;

    @Schema(description = "Path where the error occurred")
    private String path;

    @Schema(description = "Date and time of the error in ISO format")
    private String timestamp;

    public ApiError(String message, int status, String path, String errorCode) {
        this.message = message;
        this.status = status;
        this.path = path;
        this.errorCode = errorCode;
        this.timestamp = LocalDateTime.now().toString();
    }
}