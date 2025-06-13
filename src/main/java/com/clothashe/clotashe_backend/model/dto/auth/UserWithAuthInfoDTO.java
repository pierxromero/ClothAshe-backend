package com.clothashe.clotashe_backend.model.dto.auth;

import com.clothashe.clotashe_backend.model.dto.user.response.UserDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        name = "UserWithAuthInfoDTO",
        description = "DTO representing the combined user information along with authentication status."
)
public class UserWithAuthInfoDTO {

    @Schema(
            description = "Indicates whether the user's account is locked. " +
                    "If true, the user cannot log in.",
            example = "false",
            required = true
    )
    private boolean locked;

    @Schema(
            description = "Basic user information.",
            required = true
    )
    private UserDTO user;
}