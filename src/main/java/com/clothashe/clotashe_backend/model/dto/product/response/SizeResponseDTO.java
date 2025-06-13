package com.clothashe.clotashe_backend.model.dto.product.response;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(description = "DTO representing a size returned by the API.")
public class SizeResponseDTO {

    @Schema(description = "Unique identifier of the size.", example = "3")
    private Long id;

    @Schema(description = "Code representing the size.", example = "M")
    private String code;

    @Schema(description = "Optional description of the size.", example = "Medium size suitable for most adults.")
    private String description;
}