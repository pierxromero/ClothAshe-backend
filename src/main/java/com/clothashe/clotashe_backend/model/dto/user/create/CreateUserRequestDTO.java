package com.clothashe.clotashe_backend.model.dto.user.create;

import com.clothashe.clotashe_backend.model.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "CreateUserRequestDTO", description = "DTO used to create a new user.")
public class CreateUserRequestDTO {
    @NotBlank(message = "Full name is required")
    private String fullName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Phone number is required")
    private String numberPhone;

    @NotNull(message = "Role must be provided")
    private Role role;
}
