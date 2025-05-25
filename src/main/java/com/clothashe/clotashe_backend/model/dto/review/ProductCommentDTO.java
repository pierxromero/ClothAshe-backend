package com.clothashe.clotashe_backend.model.dto.review;

import com.clothashe.clotashe_backend.model.dto.product.ProductDTO;
import com.clothashe.clotashe_backend.model.dto.user.UserDTO;
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
public class ProductCommentDTO {
    private Long id;
    @NotBlank(message = "Comment must not be blank")
    @Size(max = 1000, message = "Comment must be at most 1000 characters")
    private String comment;

    @NotNull(message = "Comment date must not be null")
    private LocalDateTime commentDate;

    @NotNull(message = "Validation must not be null")
    @Min(value = 0, message = "Validation must be at least 0")
    @Max(value = 5, message = "Validation must be at most 5")
    private Integer validation;

    @NotNull(message = "User must not be null")
    @Valid
    private UserDTO user;

    @NotNull(message = "Product must not be null")
    @Valid
    private ProductDTO product;
}