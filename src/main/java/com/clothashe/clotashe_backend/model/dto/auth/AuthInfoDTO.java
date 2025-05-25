package com.clothashe.clotashe_backend.model.dto.auth;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthInfoDTO {
    private Long id;

    @NotBlank(message = "Username must not be empty.")
    @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters.")
    private String username;

    @NotBlank(message = "Password hash must not be empty.")
    private String passwordHash;

    @PastOrPresent(message = "Creation date cannot be in the future.")
    private LocalDateTime createdAt;

    @PastOrPresent(message = "Last login date cannot be in the future.")
    private LocalDateTime lastLogin;

    @NotNull(message = "Enabled status must not be null.")
    private Boolean isEnabled;

    @NotNull(message = "Locked status must not be null.")
    private Boolean isLocked;
}