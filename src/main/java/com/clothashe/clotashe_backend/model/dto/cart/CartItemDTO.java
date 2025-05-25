package com.clothashe.clotashe_backend.model.dto.cart;

import com.clothashe.clotashe_backend.model.dto.product.ProductDTO;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Data Transfer Object representing an item inside the shopping cart.")
public class CartItemDTO {

    @NotNull(message = "Quantity must be provided")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Schema(description = "Quantity of the product in the cart.", example = "2", minimum = "1")
    private Integer quantity;

    @NotNull(message = "Unit price must be provided")
    @DecimalMin(value = "0.01", inclusive = true, message = "Unit price must be greater than 0")
    @Schema(description = "Unit price of the product at the time it was added to the cart.", example = "59.99", minimum = "0.01")
    private BigDecimal unitPrice;

    @NotNull(message = "Product must be provided")
    @Valid
    @Schema(description = "Product associated with this cart item.")
    private ProductDTO productDTO;
}