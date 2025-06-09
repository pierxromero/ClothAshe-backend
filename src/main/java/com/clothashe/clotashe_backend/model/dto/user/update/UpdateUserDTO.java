package com.clothashe.clotashe_backend.model.dto.user.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(
        name = "UpdateUserDTO",
        description = "DTO used to update user information such as full name, email, and phone number."
)
public class UpdateUserDTO {

    @NotBlank
    @Size(max = 100)
    @Schema(
            description = "Full name of the user.",
            example = "Jane Doe",
            maxLength = 100,
            required = true
    )
    private String fullName;

    @NotBlank
    @Email
    @Schema(
            description = "Email address of the user.",
            example = "jane.doe@example.com",
            required = true
    )
    private String email;

    @NotBlank(message = "Phone number must not be blank")
    @Pattern(
            regexp = "^[+]?[\\d\\s\\-]{7,20}$",
            message = "Phone number must be valid (7 to 20 characters, optional leading '+')"
    )
    @Schema(
            description = "Phone number of the user. Must be 7 to 20 characters, digits, spaces, dashes, and optional leading '+'.",
            example = "+1 555-123-4567",
            required = true,
            pattern = "^[+]?[\\d\\s\\-]{7,20}$"
    )
    private String numberPhone;
}