package com.clothashe.clotashe_backend.model.dto.product.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(description = "DTO for creating a new brand.")
public class CreateBrandRequestDTO {

    @NotBlank(message = "Brand name must not be blank")
    @Size(max = 255, message = "Brand name must be at most 255 characters")
    @Schema(description = "Name of the brand.", example = "Nike", required = true, maxLength = 255)
    private String name;

    @Size(max = 2000, message = "Brand description must be at most 2000 characters")
    @Schema(description = "Optional description of the brand.", example = "Nike is a global brand known for sportswear and athletic equipment.", maxLength = 2000)
    private String description;
}