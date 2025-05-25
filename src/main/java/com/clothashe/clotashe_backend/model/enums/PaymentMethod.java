package com.clothashe.clotashe_backend.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Enumerates the available payment methods.")
public enum PaymentMethod {
    @Schema(description = "Payment made with a credit card.")
    CREDIT_CARD,

    @Schema(description = "Payment made via PayPal.")
    PAYPAL,

    @Schema(description = "Direct bank transfer.")
    BANK_TRANSFER,

    @Schema(description = "Payment made with a debit card.")
    DEBIT_CARD,

    @Schema(description = "Payment made in cash.")
    CASH
}