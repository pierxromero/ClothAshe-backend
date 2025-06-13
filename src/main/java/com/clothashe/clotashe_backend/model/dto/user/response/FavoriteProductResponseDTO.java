package com.clothashe.clotashe_backend.model.dto.user.response;
import com.clothashe.clotashe_backend.model.dto.product.response.ProductResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(description = "Data Transfer Object representing a product marked as favorite by a user.")
public class FavoriteProductResponseDTO {

    @Schema(description = "Unique identifier of the favorite record.", example = "200")
    private Long id;

    @Schema(description = "Date and time when the product was added to favorites.", example = "2024-05-20T14:00:00")
    @NotNull(message = "The date the product was added must not be null")
    @PastOrPresent(message = "The added date cannot be in the future")
    private LocalDateTime addedDate;

    @Schema(description = "User who marked the product as favorite.", required = true)
    @NotNull(message = "User information must not be null")
    @Valid
    private UserResponseDTO user;

    @Schema(description = "Product marked as favorite.", required = true)
    @NotNull(message = "Product information must not be null")
    @Valid
    private ProductResponseDTO productFavorite;
}
