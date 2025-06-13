package com.clothashe.clotashe_backend.model.dto.user.update;


import com.clothashe.clotashe_backend.model.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(
        name = "RoleChangeDTO",
        description = "DTO used to request a change in the user's role."
)
public class RoleChangeDTO {

    @NotNull
    @Schema(
            description = "New role to be assigned to the user.",
            example = "ADMIN",
            required = true
    )
    private Role newRole;
}