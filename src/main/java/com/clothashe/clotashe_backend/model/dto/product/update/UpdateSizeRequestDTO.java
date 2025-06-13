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
@Schema(description = "DTO used to update an existing size.")
public class UpdateSizeRequestDTO {

    @Size(min = 1, max = 5, message = "Size code must be between 1 and 5 characters")
    @Schema(description = "Code representing the size (e.g., S, M, L, XL).", example = "L")
    private String code;

    @Size(max = 100, message = "Description must be at most 100 characters")
    @Schema(description = "Optional description of the size.", example = "Large size for broad builds.")
    private String description;
}