package com.clothashe.clotashe_backend.model.dto.product.update;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(description = "DTO used to update an existing product.")
public class UpdateProductRequestDTO {

    @Size(max = 100, message = "Product name must be at most 100 characters")
    @Schema(description = "Name of the product.", example = "Basic White T-Shirt")
    private String name;

    @Size(max = 1000, message = "Product description must be at most 1000 characters")
    @Schema(description = "Description of the product.", example = "A comfortable T-shirt.")
    private String description;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    @Schema(description = "Product price.", example = "25.00")
    private BigDecimal price;

    @Min(value = 0, message = "Stock must be zero or more")
    @Schema(description = "Stock quantity.", example = "30")
    private Integer stock;

    @Schema(description = "Whether the product is active.", example = "false")
    private Boolean isActive;

    @Schema(description = "ID of the category.", example = "4")
    private Long categoryId;

    @Schema(description = "ID of the size.", example = "1")
    private Long sizeId;

    @Schema(description = "ID of the color.", example = "2")
    private Long colorId;

    @Schema(description = "ID of the brand.", example = "3")
    private Long brandId;
}