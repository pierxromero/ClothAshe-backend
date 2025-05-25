package com.clothashe.clotashe_backend.model.dto.cart;

import com.clothashe.clotashe_backend.model.dto.user.UserDTO;
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
public class CartDTO {
    private Long id;

    @PastOrPresent(message = "Last login date cannot be in the future.")
    private LocalDateTime createdDate;

    @NotNull(message = "Enabled active must not be null.")
    private Boolean active;

    @NotNull(message = "User must not be null.")
    @Valid
    private UserDTO user;

    @NotNull(message = "items list must not be null")
    @Size(min = 1, message = "items list must contain at least one item")
    private List<@Valid CartItemDTO> items;
}