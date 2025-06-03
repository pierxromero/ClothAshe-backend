package com.clothashe.clotashe_backend.model.dto.product.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(description = "DTO representing a brand returned in API responses.")
public class BrandResponseDTO {

    @Schema(description = "Unique identifier of the brand.", example = "10")
    private Long id;

    @Schema(description = "Name of the brand.", example = "Nike", maxLength = 255)
    private String name;

    @Schema(description = "Description of the brand.", example = "Nike is a global brand known for sportswear and athletic equipment.", maxLength = 2000)
    private String description;
}