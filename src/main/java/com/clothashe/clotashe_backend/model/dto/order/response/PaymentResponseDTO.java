package com.clothashe.clotashe_backend.model.dto.order.response;

import com.clothashe.clotashe_backend.model.enums.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(description = "Data Transfer Object representing a payment associated with an order.")
public class PaymentResponseDTO {

    @Schema(description = "Unique identifier of the payment.", example = "3001")
    private Long id;

    @NotNull(message = "Amount must not be null")
    @DecimalMin(value = "0.01", inclusive = true, message = "Amount must be greater than zero")
    @Schema(description = "Total amount paid by the customer.", example = "250.00", minimum = "0.01", required = true)
    private BigDecimal amount;

    @NotNull(message = "Payment date must not be null")
    @Schema(description = "Date and time when the payment was made.", example = "2025-05-25T10:45:00", required = true)
    private LocalDateTime paymentDate;

    @NotNull(message = "Payment status must not be null")
    @Schema(description = "Current status of the payment (e.g., COMPLETED, PENDING, FAILED).", example = "COMPLETED", required = true)
    private PaymentStatus status;

}
