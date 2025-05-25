package com.clothashe.clotashe_backend.model.dto.order;

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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(description = "Data Transfer Object representing a product item within an order.")
public class OrderItemDTO {

    @Schema(description = "Unique identifier of the order item.", example = "501")
    private Long id;

    @NotNull(message = "Order ID must not be null.")
    @Schema(description = "ID of the order this item belongs to.", example = "1001", required = true)
    private Long orderId;

    @NotNull(message = "Product must not be null.")
    @Valid
    @Schema(description = "Product associated with this order item.", required = true)
    private ProductDTO productId;

    @NotNull(message = "Quantity must not be null.")
    @Min(value = 1, message = "Quantity must be at least 1.")
    @Schema(description = "Number of units of the product ordered.", example = "3", minimum = "1", required = true)
    private Integer quantity;

    @NotNull(message = "Unit price must not be null.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Unit price must be greater than 0.")
    @Schema(description = "Unit price of the product at the time of order.", example = "49.99", minimum = "0.01", required = true)
    private BigDecimal unitPrice;

    @NotNull(message = "Subtotal must not be null.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Subtotal must be greater than 0.")
    @Schema(description = "Total price for this item (unit price × quantity).", example = "149.97", minimum = "0.01", required = true)
    private BigDecimal subtotal;
}