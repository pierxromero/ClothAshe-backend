package com.clothashe.clotashe_backend.model.dto.admin;

import com.clothashe.clotashe_backend.model.dto.auth.AuthInfoDTO;
import com.clothashe.clotashe_backend.model.enums.AdminRole;
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
@Schema(description = "Data Transfer Object representing an administrator of the system.")
public class AdminDTO {
    @Schema(description = "Unique identifier of the admin.", example = "1")
    private Long id;

    @Schema(description = "Authentication details associated with the admin.")
    @NotNull(message = "Authentication information must not be null.")
    @Valid
    private AuthInfoDTO authInfo;

    @PastOrPresent(message = "Hire date cannot be in the future.")
    @Schema(description = "Date and time when the admin was hired.", example = "2024-04-15T10:30:00")
    private LocalDateTime hireDate;

    @NotNull(message = "Admin role is required.")
    @Schema(description = "Administrative role or position held by the user.", example = "MANAGER")
    private AdminRole position;

    @NotNull(message = "Active status must not be null.")
    @Schema(description = "Whether the admin account is currently active.", example = "true")
    private Boolean isActive;
}