package com.clothashe.clotashe_backend.model.dto.product.response;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(description = "Response DTO representing a product in the store.")
public class ProductResponseDTO {

    @Schema(description = "Unique identifier of the product.", example = "101")
    private Long id;

    @Schema(description = "Name of the product.", example = "Basic White T-Shirt")
    private String name;

    @Schema(description = "Detailed description of the product.", example = "A comfortable basic white T-shirt made from 100% cotton.")
    private String description;

    @Schema(description = "Price of the product in the store currency.", example = "19.99")
    private BigDecimal price;

    @Schema(description = "Available stock quantity.", example = "50")
    private Integer stock;

    @Schema(description = "Indicates if the product is active and visible in the store.", example = "true")
    private Boolean isActive;

    @Schema(description = "Date and time when the product was created.", example = "2025-05-20T15:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "Category of the product.")
    private CategoryResponseDTO category;

    @Schema(description = "Size of the product.")
    private SizeResponseDTO size;

    @Schema(description = "Color of the product.")
    private ColorResponseDTO color;

    @Schema(description = "Brand of the product.")
    private BrandResponseDTO brand;
}