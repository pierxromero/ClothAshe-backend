package com.clothashe.clotashe_backend.model.dto.product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SizeDTO {
    private Long id;

    @NotBlank(message = "Size code must not be blank")
    @Size(min = 1, max = 5, message = "Size code must be between 1 and 5 characters")
    private String code; // Ej: S, M, L, XL

    @Size(max = 100, message = "Description must be at most 100 characters")
    private String description;
}