package com.clothashe.clotashe_backend.model.dto.user.response;

import com.clothashe.clotashe_backend.model.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "UserResponseDTO", description = "DTO returned after user registration or retrieval.")
public class UserResponseDTO {
    private Long id;
    private String fullName;
    private String email;
    private String numberPhone;
    private Role role;
}