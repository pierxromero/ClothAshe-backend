package com.clothashe.clotashe_backend.model.dto.auth;

import com.clothashe.clotashe_backend.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String numberPhone;
    private Role role;
}
