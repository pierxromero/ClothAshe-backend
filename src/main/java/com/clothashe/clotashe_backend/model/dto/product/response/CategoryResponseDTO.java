package com.clothashe.clotashe_backend.model.dto.product.response;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(description = "DTO representing a category returned by the API.")
public class CategoryResponseDTO {

    @Schema(description = "Unique identifier of the category.", example = "5")
    private Long id;

    @Schema(description = "Name of the category.", example = "T-shirts")
    private String name;

    @Schema(description = "Optional description of the category.", example = "Casual and sports T-shirts.")
    private String description;

    @Schema(description = "Indicates whether the category is active.", example = "true")
    private Boolean isActive;

    @Schema(description = "Date and time when the category was created.", example = "2025-05-01T14:30:00")
    private LocalDateTime createdAt;
}