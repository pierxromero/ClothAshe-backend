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
@Schema(description = "DTO used to create a new size.")
public class CreateSizeRequestDTO {

    @NotBlank(message = "Size code must not be blank")
    @Size(min = 1, max = 5, message = "Size code must be between 1 and 5 characters")
    @Schema(description = "Code representing the size (e.g., S, M, L, XL).", example = "M", required = true)
    private String code;

    @Size(max = 100, message = "Description must be at most 100 characters")
    @Schema(description = "Optional description of the size.", example = "Medium size suitable for most adults.")
    private String description;
}