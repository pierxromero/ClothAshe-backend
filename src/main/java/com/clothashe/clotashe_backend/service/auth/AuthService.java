package com.clothashe.clotashe_backend.service.auth;

import com.clothashe.clotashe_backend.model.dto.auth.LoginRequest;
import com.clothashe.clotashe_backend.model.dto.auth.RegisterRequest;
import com.clothashe.clotashe_backend.model.entity.user.UserEntity;

public interface AuthService {
    String register(RegisterRequest request);
    UserEntity getAuthenticatedUser();
    String login(LoginRequest request);
}