package com.clothashe.clotashe_backend.model.dto.product.response;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(description = "DTO representing a color returned by the API.")
public class ColorResponseDTO {

    @Schema(description = "Unique identifier of the color.", example = "15")
    private Long id;

    @Schema(description = "Name of the color.", example = "Red")
    private String name;

    @Schema(description = "Hexadecimal color code in the format #RRGGBB.", example = "#FF0000")
    private String hexCode;
}