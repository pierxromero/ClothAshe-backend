package com.clothashe.clotashe_backend.model.dto.user.response;
import com.clothashe.clotashe_backend.model.dto.cart.response.CartResponseDTO;
import com.clothashe.clotashe_backend.model.dto.order.response.OrderResponseDTO;
import com.clothashe.clotashe_backend.model.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

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


    @Schema(description = "Shopping cart associated with the user.")
    @Valid
    private CartResponseDTO cart;

    @Schema(description = "List of products marked as favorites by the user.")
    private List<@Valid FavoriteProductResponseDTO> favorites;

    @Schema(description = "List of addresses associated with the user.")
    @NotEmpty(message = "Addresses list must contain at least one item")
    private List<@Valid AddressResponseDTO> addresses;

    @Schema(description = "Role assigned to the user.", example = "CLIENT", required = true)
    @NotNull(message = "Role must be assigned")
    private Role role;

    @Schema(description = "List of user's purchase history (orders).")
    private List<@Valid OrderResponseDTO> purchaseHistory;
}