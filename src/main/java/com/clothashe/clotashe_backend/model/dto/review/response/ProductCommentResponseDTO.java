package com.clothashe.clotashe_backend.model.dto.review.response;

import com.clothashe.clotashe_backend.model.dto.product.response.ProductResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO de respuesta para un comentario de producto.")
public class ProductCommentResponseDTO {

    @Schema(description = "Unique identifier of the product comment.", example = "123")
    private Long id;

    @Schema(description = "Text content of the comment.", example = "Great quality and fit!", maxLength = 1000)
    private String comment;

    @Schema(description = "Date and time when the comment was made.", example = "2024-05-20T15:30:00")
    private LocalDateTime commentDate;

    @Schema(description = "Validation rating given by the user, from 0 to 5.", example = "4", minimum = "0", maximum = "5")
    private Integer validation;

    @Schema(description = "User ID who made the comment.", example = "55")
    private Long userId;

    @Schema(description = "Product details the comment is associated with.")
    private ProductResponseDTO product;
}
