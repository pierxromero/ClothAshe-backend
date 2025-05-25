package com.clothashe.clotashe_backend.model.dto.user;
import com.clothashe.clotashe_backend.model.dto.auth.AuthInfoDTO;
import com.clothashe.clotashe_backend.model.dto.cart.CartDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserDTO {

    private Long id;

    @NotBlank(message = "Full name must not be blank")
    @Size(max = 100, message = "Full name must not exceed 100 characters")
    private String fullName;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Phone number must not be blank")
    @Pattern(
            regexp = "^[+]?[\\d\\s\\-]{7,20}$",
            message = "Phone number must be valid (7 to 15 digits, optional leading '+')"
    )
    private String numberPhone;

    @NotNull(message = "Authentication info must not be null")
    @Valid
    private AuthInfoDTO authInfoId;

    private LocalDateTime lastLoginDate;

    @Valid
    private CartDTO cartId;

    private List<@Valid FavoriteProductDTO> favoriteProducts;

    @NotEmpty(message = "Addresses list must contain at least one item")
    private List<@Valid AddressDTO> addresses;
}