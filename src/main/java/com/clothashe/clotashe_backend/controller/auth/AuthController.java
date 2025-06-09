package com.clothashe.clotashe_backend.controller.auth;

import com.clothashe.clotashe_backend.exception.error.ApiError;
import com.clothashe.clotashe_backend.model.dto.auth.LoginRequest;
import com.clothashe.clotashe_backend.model.dto.auth.LoginResponse;
import com.clothashe.clotashe_backend.model.dto.auth.RegisterRequest;
import com.clothashe.clotashe_backend.service.auth.AuthService;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(
            summary = "Login user",
            description = "Authenticate a user and return a JWT token."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful login",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponse.class),
                            examples = @ExampleObject(value = """
                    {
                      "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid request or credentials",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Invalid email or password",
                      "status": 400,
                      "errorCode": "BAD_CREDENTIALS",
                      "path": "/auth/login",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Unexpected error",
                      "status": 500,
                      "errorCode": "INTERNAL_SERVER_ERROR",
                      "path": "/auth/login",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            )
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Login request with email and password",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginRequest.class),
                            examples = @ExampleObject(value = """
                    {
                      "email": "user@example.com",
                      "password": "mySecurePassword123"
                    }
                    """
                            )
                    )
            )
            @RequestBody LoginRequest request
    ) {
        String jwtToken = authService.login(request);
        return ResponseEntity.ok(new LoginResponse(jwtToken));
    }

    @Operation(
            summary = "Register new user",
            description = "Register a new user and return a JWT token."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful registration",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponse.class),
                            examples = @ExampleObject(value = """
                    {
                      "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Email format is invalid",
                      "status": 400,
                      "errorCode": "VALIDATION_ERROR",
                      "path": "/auth/register",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "409", description = "User already exists",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "User with email 'newuser@example.com' already exists",
                      "status": 409,
                      "errorCode": "USER_ALREADY_EXISTS",
                      "path": "/auth/register",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Unexpected error",
                      "status": 500,
                      "errorCode": "INTERNAL_SERVER_ERROR",
                      "path": "/auth/register",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            )
    })
    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User registration data",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RegisterRequest.class),
                            examples = @ExampleObject(value = """
                    {
                      "email": "newuser@example.com",
                      "password": "password123",
                      "firstName": "John",
                      "lastName": "Doe",
                      "numberPhone": "+54 911 2345 6789"
                    }
                    """
                            )
                    )
            )
            @RequestBody RegisterRequest request
    ) {
        String jwtToken = authService.register(request);
        return ResponseEntity.ok(new LoginResponse(jwtToken));
    }
}