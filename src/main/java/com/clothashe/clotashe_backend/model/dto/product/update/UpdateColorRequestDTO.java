package com.clothashe.clotashe_backend.model.dto.product.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(description = "DTO used to update an existing color.")
public class UpdateColorRequestDTO {

    @Size(max = 100, message = "Color name must be at most 100 characters")
    @Schema(description = "Name of the color.", example = "Dark Red", maxLength = 100)
    private String name;

    @Pattern(regexp = "^#([A-Fa-f0-9]{6})$", message = "Hex code must be a valid hexadecimal color (e.g., #FFFFFF)")
    @Schema(description = "Hexadecimal color code in the format #RRGGBB.", example = "#8B0000", pattern = "^#([A-Fa-f0-9]{6})$")
    private String hexCode;
}