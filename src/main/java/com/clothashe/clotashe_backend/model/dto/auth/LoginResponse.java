package com.clothashe.clotashe_backend.model.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "JWT token returned after successful login or registration")
public class LoginResponse {

    @Schema(description = "JWT authentication token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...", required = true)
    private String token;

    public LoginResponse() {
    }

    public LoginResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
