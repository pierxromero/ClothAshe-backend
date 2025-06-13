package com.clothashe.clotashe_backend.model.dto.review.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO to update a product comment.")
public class UpdateProductCommentRequestDTO {

    @Size(max = 1000, message = "Comment must be at most 1000 characters")
    @Schema(description = "Text content of the comment.", example = "Great quality and fit!", maxLength = 1000)
    private String comment;

    @Min(value = 0, message = "Validation must be at least 0")
    @Max(value = 5, message = "Validation must be at most 5")
    @Schema(description = "Validation rating given by the user, from 0 to 5.", example = "4", minimum = "0", maximum = "5")
    private Integer validation;

    @Schema(description = "ID of the product the comment is associated with.", example = "101")
    private Long productId;
}