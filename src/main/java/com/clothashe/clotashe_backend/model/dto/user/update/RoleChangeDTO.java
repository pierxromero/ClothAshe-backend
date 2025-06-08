package com.clothashe.clotashe_backend.model.dto.user.update;


import com.clothashe.clotashe_backend.model.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RoleChangeDTO {
    @NotNull
    private Role newRole;
}