package com.clothashe.clotashe_backend.model.dto.cart.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO used to update a cart item.")
public class UpdateCartItemRequestDTO {

    @NotNull(message = "Quantity must be provided")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Schema(description = "Updated quantity for the product", example = "2")
    private Integer quantity;

}