package com.clothashe.clotashe_backend.model.dto.user.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ChangePasswordDTO {

    @NotBlank(message = "Password must not be blank")
    private String currentPassword;

    @NotBlank
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String newPassword;
}
