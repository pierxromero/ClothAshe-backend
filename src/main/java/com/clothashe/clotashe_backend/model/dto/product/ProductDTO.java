package com.clothashe.clotashe_backend.model.dto.product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(description = "Data Transfer Object representing a product in the store.")
public class ProductDTO {

    @Schema(description = "Unique identifier of the product.", example = "101")
    private Long id;

    @NotBlank(message = "Product name must not be blank")
    @Size(max = 100, message = "Product name must be at most 100 characters")
    @Schema(description = "Name of the product.", example = "Basic White T-Shirt", maxLength = 100, required = true)
    private String name;

    @NotBlank(message = "Product description must not be blank")
    @Size(max = 1000, message = "Product description must be at most 1000 characters")
    @Schema(description = "Detailed description of the product.", example = "A comfortable basic white T-shirt made from 100% cotton.", maxLength = 1000, required = true)
    private String description;

    @NotNull(message = "Product price must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Product price must be greater than zero")
    @Schema(description = "Price of the product in the store currency.", example = "19.99", required = true)
    private BigDecimal price;

    @NotNull(message = "Stock must not be null")
    @Min(value = 0, message = "Stock must be zero or more")
    @Schema(description = "Available stock quantity.", example = "50", required = true)
    private Integer stock;

    @NotNull(message = "Product active status must not be null")
    @Schema(description = "Indicates if the product is active and visible in the store.", example = "true", required = true)
    private Boolean isActive;

    @Schema(description = "Date and time when the product was created.", example = "2025-05-20T15:00:00")
    private LocalDateTime createdAt;

    @NotNull(message = "Category must not be null")
    @Valid
    @Schema(description = "Category of the product.", required = true)
    private CategoryDTO category;

    @NotNull(message = "Size must not be null")
    @Valid
    @Schema(description = "Size details of the product.", required = true)
    private SizeDTO size;

    @NotNull(message = "Color must not be null")
    @Valid
    @Schema(description = "Color details of the product.", required = true)
    private ColorDTO color;

    @NotNull(message = "Brand must not be null")
    @Valid
    @Schema(description = "Brand details of the product.", required = true)
    private BrandDTO brand;
}