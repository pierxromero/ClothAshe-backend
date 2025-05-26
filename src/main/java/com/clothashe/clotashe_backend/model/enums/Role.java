package com.clothashe.clotashe_backend.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Possible roles assigned to an administrator.")
public enum Role {
    @Schema(description = "Full system access and management capabilities.")
    ADMIN,

    @Schema(description = "Limited administrative access, typically department-level.")
    MANAGER,

    @Schema(description = "Regular user with access to shopping, reviews, and personal account management.")
    CLIENT
}