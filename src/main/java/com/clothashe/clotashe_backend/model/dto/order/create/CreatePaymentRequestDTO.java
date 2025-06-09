package com.clothashe.clotashe_backend.model.dto.order.create;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        name = "CreatePaymentRequestDTO",
        description = "DTO used to request the creation of a payment for an order."
)
public class CreatePaymentRequestDTO {

    @NotNull
    @Schema(
            description = "ID of the order to which the payment belongs.",
            example = "12345",
            required = true
    )
    private Long orderId;

    @NotNull
    @Schema(
            description = "Payment amount to be processed.",
            example = "150.75",
            required = true
    )
    private BigDecimal amount;
}