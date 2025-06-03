package com.clothashe.clotashe_backend.model.dto.order.create;

import com.clothashe.clotashe_backend.model.enums.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePaymentRequestDTO {
    @NotNull
    private Long orderId;

    @NotNull
    private BigDecimal amount;

}
