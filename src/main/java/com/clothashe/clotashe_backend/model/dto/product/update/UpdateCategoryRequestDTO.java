package com.clothashe.clotashe_backend.model.dto.product.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(description = "DTO used to update an existing category.")
public class UpdateCategoryRequestDTO {

    @Size(max = 255, message = "Category name must be at most 255 characters")
    @Schema(description = "Name of the category.", example = "T-shirts", maxLength = 255)
    private String name;

    @Size(max = 2000, message = "Category description must be at most 2000 characters")
    @Schema(description = "Optional description of the category.", example = "Updated description.", maxLength = 2000)
    private String description;

    @Schema(description = "Indicates whether the category is active.", example = "false")
    private Boolean isActive;
}
