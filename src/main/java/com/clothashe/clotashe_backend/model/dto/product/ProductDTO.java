package com.clothashe.clotashe_backend.model.dto.product;
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
public class ProductDTO {
    private Long id;
    @NotBlank(message = "Product name must not be blank")
    @Size(max = 100, message = "Product name must be at most 100 characters")
    private String name;

    @NotBlank(message = "Product description must not be blank")
    @Size(max = 1000, message = "Product description must be at most 1000 characters")
    private String description;

    @NotNull(message = "Product price must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Product price must be greater than zero")
    private BigDecimal price;

    @NotNull(message = "Stock must not be null")
    @Min(value = 0, message = "Stock must be zero or more")
    private Integer stock;

    @NotNull(message = "Product active status must not be null")
    private Boolean isActive;

    private LocalDateTime createdAt;

    @NotNull(message = "Category must not be null")
    @Valid
    private CategoryDTO category;

    @NotNull(message = "Size must not be null")
    @Valid
    private SizeDTO size;

    @NotNull(message = "Color must not be null")
    @Valid
    private ColorDTO color;

    @NotNull(message = "Brand must not be null")
    @Valid
    private BrandDTO brand;
}