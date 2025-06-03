package com.clothashe.clotashe_backend.model.dto.cart.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCartItemRequestDTO {
    @NotNull(message = "Product ID must be provided")
    @Schema(description = "ID of the product to add", example = "15")
    private Long productId;

    @NotNull(message = "Quantity must be provided")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Schema(description = "Quantity of the product to add", example = "3")
    private Integer quantity;
}
