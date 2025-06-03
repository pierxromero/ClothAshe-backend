package com.clothashe.clotashe_backend.model.dto.order.create;

import com.clothashe.clotashe_backend.model.enums.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequestDTO {
    @NotNull(message = "Shipping address is required")
    @Valid
    @Schema(description = "Shipping address associated with the order.", required = true)
    private Long shippingAddressId;

    @NotNull(message = "Payment method is required")
    @Schema(description = "Method used to process the payment.", example = "CREDIT_CARD", required = true)
    private PaymentMethod paymentMethod;
}
