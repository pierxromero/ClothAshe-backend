package com.clothashe.clotashe_backend.model.dto.auth;

import com.clothashe.clotashe_backend.model.dto.user.response.UserResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object containing authentication details of a user.")
public class AuthInfoDTO {

    @Schema(description = "Unique identifier of the authentication record.", example = "1")
    private Long id;

    @NotBlank(message = "Email must not be empty.")
    @Email(message = "Email should be valid.")
    @Size(min = 4, max = 50, message = "Email must be between 4 and 50 characters.")
    @Schema(description = "Email associated with the account.", example = "john@example.com", minLength = 4, maxLength = 50)
    private String email;

    @NotBlank(message = "Password hash must not be empty.")
    @Schema(description = "Hashed password used for authentication.", example = "$2a$10$WzY...sQH")
    private String passwordHash;

    @PastOrPresent(message = "Creation date cannot be in the future.")
    @Schema(description = "Date and time when the account was created.", example = "2024-11-19T10:30:00")
    private LocalDateTime createdAt;

    @NotNull(message = "Enabled status must not be null.")
    @Schema(description = "Indicates whether the account is currently enabled.", example = "true")
    private Boolean isEnabled;

    @NotNull(message = "Locked status must not be null.")
    @Schema(description = "Indicates whether the account is currently locked.", example = "false")
    private Boolean isLocked;

    @Schema(description = "Authentication information including linked user.")
    @Valid
    @NotNull(message = "User must be linked to auth info.")
    private UserResponseDTO user;
}