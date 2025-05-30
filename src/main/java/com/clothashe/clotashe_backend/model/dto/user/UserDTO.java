package com.clothashe.clotashe_backend.model.dto.user;
import com.clothashe.clotashe_backend.model.dto.auth.AuthInfoDTO;
import com.clothashe.clotashe_backend.model.dto.cart.CartDTO;
import com.clothashe.clotashe_backend.model.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Data Transfer Object representing a user in the system.")
public class UserDTO {

    @Schema(description = "Unique identifier of the user.", example = "10")
    private Long id;

    @Schema(description = "Full name of the user.", example = "Piero Romero", maxLength = 100)
    @NotBlank(message = "Full name must not be blank")
    @Size(max = 100, message = "Full name must not exceed 100 characters")
    private String fullName;

    @Schema(description = "User's email address.", example = "piero.romero@example.com", format = "email")
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be valid")
    private String email;

    @Schema(description = "User's phone number with optional leading '+'.", example = "+541123456789", pattern = "^[+]?[\\d\\s\\-]{7,20}$")
    @NotBlank(message = "Phone number must not be blank")
    @Pattern(
            regexp = "^[+]?[\\d\\s\\-]{7,20}$",
            message = "Phone number must be valid (7 to 15 digits, optional leading '+')"
    )
    private String numberPhone;


    @Schema(description = "Date and time of the last login.", example = "2025-05-24T18:45:00")
    private LocalDateTime lastLoginDate;

    @Schema(description = "Shopping cart associated with the user.")
    @Valid
    private CartDTO cartId;

    @Schema(description = "List of products marked as favorites by the user.")
    private List<@Valid FavoriteProductDTO> favoriteProducts;

    @Schema(description = "List of addresses associated with the user.")
    @NotEmpty(message = "Addresses list must contain at least one item")
    private List<@Valid AddressDTO> addresses;

    @Schema(description = "Role assigned to the user.", example = "CLIENT", required = true)
    @NotNull(message = "Role must be assigned")
    private Role role;  // Campo role agregado
}