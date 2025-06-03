package com.clothashe.clotashe_backend.model.dto.product.create;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(description = "DTO used to create a new product.")
public class CreateProductRequestDTO {

    @NotBlank(message = "Product name must not be blank")
    @Size(max = 100, message = "Product name must be at most 100 characters")
    @Schema(description = "Name of the product.", example = "Basic White T-Shirt", required = true)
    private String name;

    @NotBlank(message = "Product description must not be blank")
    @Size(max = 1000, message = "Product description must be at most 1000 characters")
    @Schema(description = "Detailed description of the product.", example = "A comfortable basic white T-shirt made from 100% cotton.", required = true)
    private String description;

    @NotNull(message = "Product price must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Product price must be greater than zero")
    @Schema(description = "Price of the product.", example = "19.99", required = true)
    private BigDecimal price;

    @NotNull(message = "Stock must not be null")
    @Min(value = 0, message = "Stock must be zero or more")
    @Schema(description = "Available stock quantity.", example = "50", required = true)
    private Integer stock;

    @NotNull(message = "Product active status must not be null")
    @Schema(description = "Indicates if the product is active.", example = "true", required = true)
    private Boolean isActive;

    @NotNull(message = "Category ID must not be null")
    @Schema(description = "ID of the category.", example = "3", required = true)
    private Long categoryId;

    @NotNull(message = "Size ID must not be null")
    @Schema(description = "ID of the size.", example = "2", required = true)
    private Long sizeId;

    @NotNull(message = "Color ID must not be null")
    @Schema(description = "ID of the color.", example = "7", required = true)
    private Long colorId;

    @NotNull(message = "Brand ID must not be null")
    @Schema(description = "ID of the brand.", example = "1", required = true)
    private Long brandId;
}