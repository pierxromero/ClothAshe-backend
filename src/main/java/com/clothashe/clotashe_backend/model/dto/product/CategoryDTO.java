package com.clothashe.clotashe_backend.model.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CategoryDTO {

    private Long id;

    @NotBlank(message = "Category name must not be blank")
    @Size(max = 255, message = "Category name must be at most 255 characters")
    private String name;

    @Size(max = 2000, message = "Category description must be at most 2000 characters")
    private String description;

    @NotNull(message = "Category active status must not be null")
    private Boolean isActive;

    private LocalDateTime createdAt;
}