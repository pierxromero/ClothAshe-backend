package com.clothashe.clotashe_backend.model.dto.order.response;


import com.clothashe.clotashe_backend.model.dto.user.response.AddressResponseDTO;
import com.clothashe.clotashe_backend.model.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(description = "Data Transfer Object representing a customer's order.")
public class OrderResponseDTO {

    @Schema(description = "Unique identifier of the order.", example = "1001")
    private Long id;

    @NotNull(message = "Order date is required")
    @PastOrPresent(message = "Order date cannot be in the future")
    @Schema(description = "Date and time when the order was placed.", example = "2025-05-24T12:30:00", required = true)
    private LocalDateTime orderDate;

    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.01", inclusive = true, message = "Total amount must be greater than 0")
    @Schema(description = "Total amount of the order.", example = "199.99", minimum = "0.01", required = true)
    private BigDecimal totalAmount;

    @NotNull(message = "Order status is required")
    @Schema(description = "Current status of the order (e.g., PENDING, SHIPPED, DELIVERED).", example = "PENDING", required = true)
    private OrderStatus status;

    @NotNull(message = "Shipping address is required")
    @Valid
    @Schema(description = "Shipping address associated with the order.", required = true)
    private AddressResponseDTO shippingAddress;

    @NotEmpty(message = "Items list must contain at least one item")
    @Schema(description = "List of items included in the order.", required = true)
    private List<@Valid OrderItemResponseDTO> items;

    @NotNull(message = "Payment information must be provided")
    @Valid
    @Schema(description = "Payment details associated with the order.", required = true)
    private PaymentResponseDTO payment;
}
