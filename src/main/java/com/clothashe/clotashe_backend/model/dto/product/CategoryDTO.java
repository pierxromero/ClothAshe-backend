package com.clothashe.clotashe_backend.model.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(description = "Data Transfer Object representing a product category.")
public class CategoryDTO {

    @Schema(description = "Unique identifier of the category.", example = "5")
    private Long id;

    @NotBlank(message = "Category name must not be blank")
    @Size(max = 255, message = "Category name must be at most 255 characters")
    @Schema(description = "Name of the category.", example = "T-shirts", maxLength = 255, required = true)
    private String name;

    @Size(max = 2000, message = "Category description must be at most 2000 characters")
    @Schema(description = "Optional description of the category.", example = "Casual and sports T-shirts for men and women.", maxLength = 2000)
    private String description;

    @NotNull(message = "Category active status must not be null")
    @Schema(description = "Indicates whether the category is active.", example = "true", required = true)
    private Boolean isActive;

    @Schema(description = "Date and time when the category was created.", example = "2025-05-01T14:30:00")
    private LocalDateTime createdAt;
}