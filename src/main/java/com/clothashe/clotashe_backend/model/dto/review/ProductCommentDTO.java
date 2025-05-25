package com.clothashe.clotashe_backend.model.dto.review;

import com.clothashe.clotashe_backend.model.dto.product.ProductDTO;
import com.clothashe.clotashe_backend.model.dto.user.UserDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object representing a comment on a product.")
public class ProductCommentDTO {

    @Schema(description = "Unique identifier of the product comment.", example = "123")
    private Long id;

    @Schema(description = "Text content of the comment.", example = "Great quality and fit!", maxLength = 1000)
    @NotBlank(message = "Comment must not be blank")
    @Size(max = 1000, message = "Comment must be at most 1000 characters")
    private String comment;

    @Schema(description = "Date and time when the comment was made.", example = "2024-05-20T15:30:00")
    @NotNull(message = "Comment date must not be null")
    private LocalDateTime commentDate;

    @Schema(description = "Validation rating given by the user, from 0 to 5.", example = "4", minimum = "0", maximum = "5")
    @NotNull(message = "Validation must not be null")
    @Min(value = 0, message = "Validation must be at least 0")
    @Max(value = 5, message = "Validation must be at most 5")
    private Integer validation;

    @Schema(description = "User who made the comment.", required = true)
    @NotNull(message = "User must not be null")
    @Valid
    private UserDTO user;

    @Schema(description = "Product the comment is associated with.", required = true)
    @NotNull(message = "Product must not be null")
    @Valid
    private ProductDTO product;
}