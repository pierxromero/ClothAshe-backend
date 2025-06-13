package com.clothashe.clotashe_backend.model.dto.review.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO to create a product review.")
public class CreateProductCommentRequestDTO {

    @NotBlank(message = "Comment must not be blank")
    @Size(max = 1000, message = "Comment must be at most 1000 characters")
    @Schema(description = "Text content of the comment.", example = "Great quality and fit!", maxLength = 1000, required = true)
    private String comment;

    @NotNull(message = "Validation must not be null")
    @Min(value = 0, message = "Validation must be at least 0")
    @Max(value = 5, message = "Validation must be at most 5")
    @Schema(description = "Validation rating given by the user, from 0 to 5.", example = "4", minimum = "0", maximum = "5", required = true)
    private Integer validation;

    @NotNull(message = "Product ID must not be null")
    @Schema(description = "ID of the product the comment is associated with.", example = "101", required = true)
    private Long productId;
}