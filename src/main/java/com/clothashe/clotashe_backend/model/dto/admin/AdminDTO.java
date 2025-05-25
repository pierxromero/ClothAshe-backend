package com.clothashe.clotashe_backend.model.dto.admin;

import com.clothashe.clotashe_backend.model.dto.auth.AuthInfoDTO;
import com.clothashe.clotashe_backend.model.enums.AdminRole;
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
public class AdminDTO {
    private Long id;

    @NotNull(message = "Authentication information must not be null.")
    @Valid
    private AuthInfoDTO authInfo;

    @PastOrPresent(message = "Hire date cannot be in the future.")
    private LocalDateTime hireDate;

    @NotNull(message = "Admin role is required.")
    private AdminRole position;

    @NotNull(message = "Active status must not be null.")
    private Boolean isActive;
}