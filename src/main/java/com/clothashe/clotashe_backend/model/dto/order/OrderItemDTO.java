package com.clothashe.clotashe_backend.model.dto.order;

import com.clothashe.clotashe_backend.model.dto.product.ProductDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderItemDTO {

    private Long id;

    @NotNull(message = "Order ID must not be null.")
    private Long orderId;

    @NotNull(message = "Product must not be null.")
    @Valid
    private ProductDTO productId;

    @NotNull(message = "Quantity must not be null.")
    @Min(value = 1, message = "Quantity must be at least 1.")
    private Integer quantity;

    @NotNull(message = "Unit price must not be null.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Unit price must be greater than 0.")
    private BigDecimal unitPrice;

    @NotNull(message = "Subtotal must not be null.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Subtotal must be greater than 0.")
    private BigDecimal subtotal;
}