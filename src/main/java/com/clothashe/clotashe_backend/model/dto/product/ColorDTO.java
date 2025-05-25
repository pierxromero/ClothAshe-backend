package com.clothashe.clotashe_backend.model.dto.product;

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
public class ColorDTO {

    private Long id;
    @NotBlank(message = "Color name must not be blank")
    @Size(max = 100, message = "Color name must be at most 100 characters")
    private String name;

    @NotBlank(message = "Hex code must not be blank")
    @Pattern(regexp = "^#([A-Fa-f0-9]{6})$", message = "Hex code must be a valid hexadecimal color (e.g., #FFFFFF)")
    private String hexCode;
}
