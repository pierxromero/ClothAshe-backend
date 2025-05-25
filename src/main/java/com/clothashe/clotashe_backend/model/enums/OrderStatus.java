package com.clothashe.clotashe_backend.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Represents the current status of an order.")
public enum OrderStatus {
    @Schema(description = "Order has been created but not yet processed.")
    PENDING,

    @Schema(description = "Order is currently being processed.")
    PROCESSING,

    @Schema(description = "Order has been shipped to the customer.")
    SHIPPED,

    @Schema(description = "Order has been delivered to the customer.")
    DELIVERED,

    @Schema(description = "Order has been cancelled.")
    CANCELLED,

    @Schema(description = "Order has been returned by the customer.")
    RETURNED
}