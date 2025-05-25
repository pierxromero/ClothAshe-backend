package com.clothashe.clotashe_backend.model.dto.order;

import com.clothashe.clotashe_backend.model.enums.PaymentMethod;
import com.clothashe.clotashe_backend.model.enums.PaymentStatus;
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
public class PaymentDTO {
    private Long id;

    @NotNull(message = "Amount must not be null")
    @DecimalMin(value = "0.01", inclusive = true, message = "Amount must be greater than zero")
    private BigDecimal amount;

    @NotNull(message = "Payment method must not be null")
    private PaymentMethod method;

    @NotNull(message = "Payment date must not be null")
    private LocalDateTime paymentDate;

    @NotNull(message = "Payment status must not be null")
    private PaymentStatus status;

    @NotNull(message = "Order ID must not be null")
    private Long orderId;
}
