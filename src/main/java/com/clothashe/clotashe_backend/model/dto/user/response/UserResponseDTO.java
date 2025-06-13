package com.clothashe.clotashe_backend.model.dto.user.response;

import com.clothashe.clotashe_backend.model.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        name = "UserResponseDTO",
        description = "DTO returned after user registration or retrieval, containing user details."
)
public class UserResponseDTO {

    @Schema(
            description = "Unique identifier of the user.",
            example = "101",
            required = true
    )
    private Long id;

    @Schema(
            description = "Full name of the user.",
            example = "John Doe",
            required = true
    )
    private String fullName;

    @Schema(
            description = "Email address of the user.",
            example = "john.doe@example.com",
            required = true
    )
    private String email;

    @Schema(
            description = "Phone number of the user.",
            example = "+1-555-123-4567",
            required = false
    )
    private String numberPhone;

    @Schema(
            description = "Role assigned to the user.",
            example = "USER",
            required = true
    )
    private Role role;
}