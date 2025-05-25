package com.clothashe.clotashe_backend.model.dto.cart;

import com.clothashe.clotashe_backend.model.dto.product.ProductDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDTO {

    @NotNull(message = "Quantity must be provided")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "Unit price must be provided")
    @DecimalMin(value = "0.01", inclusive = true, message = "Unit price must be greater than 0")
    private BigDecimal unitPrice;

    @NotNull(message = "Product must be provided")
    @Valid
    private ProductDTO productDTO;
}