package com.clothashe.clotashe_backend.model.dto.user.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(
        name = "ChangePasswordDTO",
        description = "DTO used to request a password change, containing current and new passwords."
)
public class ChangePasswordDTO {

    @NotBlank(message = "Password must not be blank")
    @Schema(
            description = "Current password of the user.",
            example = "OldPassword123",
            required = true
    )
    private String currentPassword;

    @NotBlank
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Schema(
            description = "New password with a minimum length of 6 characters.",
            example = "NewPassword456",
            required = true
    )
    private String newPassword;
}