package com.clothashe.clotashe_backend.model.dto.user.update;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UpdateUserDTO {
    @NotBlank
    @Size(max = 100) private String fullName;
    @NotBlank
    @Email
    private String email;

    @NotBlank(message = "Phone number must not be blank")
    @Pattern(
            regexp = "^[+]?[\\d\\s\\-]{7,20}$",
            message = "Phone number must be valid (7 to 20 characters, optional leading '+')"
    )
    private String numberPhone;
}