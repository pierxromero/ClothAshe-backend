package com.clothashe.clotashe_backend.model.dto.user;
import com.clothashe.clotashe_backend.model.dto.product.ProductDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FavoriteProductDTO {

    private Long id;

    @NotNull(message = "The date the product was added must not be null")
    @PastOrPresent(message = "The added date cannot be in the future")
    private LocalDateTime addedDate;

    @NotNull(message = "User information must not be null")
    @Valid
    private UserDTO userId;

    @NotNull(message = "Product information must not be null")
    @Valid
    private ProductDTO productId;
}
