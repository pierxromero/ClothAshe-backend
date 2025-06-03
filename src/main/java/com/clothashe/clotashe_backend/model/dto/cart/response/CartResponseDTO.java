package com.clothashe.clotashe_backend.model.dto.cart.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object representing a user's shopping cart.")
public class CartResponseDTO {

    @Schema(description = "Unique identifier of the cart.", example = "101")
    private Long id;

    @PastOrPresent(message = "Last login date cannot be in the future.")
    @Schema(description = "Date and time when the cart was created.", example = "2025-05-24T14:45:00")
    private LocalDateTime createdDate;

    @NotNull(message = "Enabled active must not be null.")
    @Schema(description = "Indicates whether the cart is currently active.", example = "true")
    private Boolean active;


    @NotNull(message = "Items list must not be null.")
    @Schema(description = "List of items included in the cart.")
    private List<@Valid CartItemResponseDTO> items;
}