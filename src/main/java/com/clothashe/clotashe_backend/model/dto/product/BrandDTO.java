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
public class BrandDTO {

    private Long id;

    @NotBlank(message = "Brand name must not be blank")
    @Size(max = 255, message = "Brand name must be at most 255 characters")
    private String name;

    @Size(max = 2000, message = "Brand description must be at most 2000 characters")
    private String description;
}