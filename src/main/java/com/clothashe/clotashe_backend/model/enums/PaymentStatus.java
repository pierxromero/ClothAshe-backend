package com.clothashe.clotashe_backend.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Represents the approval state of a payment.")
public enum PaymentStatus {
    @Schema(description = "Payment has been initiated but not confirmed.")
    PENDING,

    @Schema(description = "Payment has been successfully approved.")
    APPROVED,

    @Schema(description = "Payment has been declined.")
    DECLINED
}