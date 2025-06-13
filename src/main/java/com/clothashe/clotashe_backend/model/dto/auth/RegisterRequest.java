package com.clothashe.clotashe_backend.model.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request body for user registration")
public class RegisterRequest {

    @NotBlank(message = "Password must not be blank")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Schema(description = "User's password", example = "password123", minLength = 6, required = true)
    private String password;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email format is invalid")
    @Schema(description = "User's email address", example = "newuser@example.com", required = true)
    private String email;

    @NotBlank(message = "First name must not be blank")
    @Schema(description = "User's first name", example = "John", required = true)
    private String firstName;

    @NotBlank(message = "Last name must not be blank")
    @Schema(description = "User's last name", example = "Doe", required = true)
    private String lastName;

    @NotBlank(message = "Phone number must not be blank")
    @Pattern(
            regexp = "^[+]?[\\d\\s\\-]{7,20}$",
            message = "Phone number must be valid (7 to 20 characters, optional leading '+')"
    )
    @Schema(description = "User's phone number", example = "+54 911 2345 6789", required = true)
    private String numberPhone;

}
