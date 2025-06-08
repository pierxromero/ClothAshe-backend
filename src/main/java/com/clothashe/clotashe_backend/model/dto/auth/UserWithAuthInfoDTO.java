package com.clothashe.clotashe_backend.model.dto.auth;

import com.clothashe.clotashe_backend.model.dto.user.response.UserDTO;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserWithAuthInfoDTO {
    private boolean locked;
    private UserDTO user;
}
