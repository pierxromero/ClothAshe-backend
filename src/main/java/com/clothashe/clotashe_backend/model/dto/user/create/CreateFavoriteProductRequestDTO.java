package com.clothashe.clotashe_backend.model.dto.user.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO used to mark a product as favorite.")
public class CreateFavoriteProductRequestDTO {

    @Schema(description = "ID of the product to be marked as favorite", required = true, example = "101")
    @NotNull(message = "Product ID must not be null")
    private Long productId;
}