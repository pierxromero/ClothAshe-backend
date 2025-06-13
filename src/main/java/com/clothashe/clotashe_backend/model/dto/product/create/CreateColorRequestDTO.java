package com.clothashe.clotashe_backend.model.dto.product.create;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(description = "DTO used to create a new color.")
public class CreateColorRequestDTO {

    @NotBlank(message = "Color name must not be blank")
    @Size(max = 100, message = "Color name must be at most 100 characters")
    @Schema(description = "Name of the color.", example = "Red", maxLength = 100, required = true)
    private String name;

    @NotBlank(message = "Hex code must not be blank")
    @Pattern(regexp = "^#([A-Fa-f0-9]{6})$", message = "Hex code must be a valid hexadecimal color (e.g., #FFFFFF)")
    @Schema(description = "Hexadecimal color code in the format #RRGGBB.", example = "#FF0000", required = true, pattern = "^#([A-Fa-f0-9]{6})$")
    private String hexCode;
}