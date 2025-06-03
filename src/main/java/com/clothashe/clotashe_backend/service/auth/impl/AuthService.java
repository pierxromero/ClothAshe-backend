package com.clothashe.clotashe_backend.service.auth.impl;

import com.clothashe.clotashe_backend.model.dto.user.create.CreateUserRequestDTO;
import com.clothashe.clotashe_backend.security.JwtService;
import com.clothashe.clotashe_backend.model.dto.auth.RegisterRequest;
import com.clothashe.clotashe_backend.model.entity.auth.AuthInfoEntity;
import com.clothashe.clotashe_backend.model.entity.user.UserEntity;
import com.clothashe.clotashe_backend.repository.auth.AuthInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserServiceImpl userServiceImpl;
    private final AuthInfoRepository authInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public String register(RegisterRequest request) {

        if (authInfoRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        CreateUserRequestDTO userDto = CreateUserRequestDTO.builder()
                .fullName(request.getFirstName() + " " + request.getLastName())
                .email(request.getEmail())
                .numberPhone(request.getNumberPhone())
                .role(request.getRole())
                .build();

        UserEntity user = userServiceImpl.createUser(userDto);

        AuthInfoEntity authInfo = AuthInfoEntity.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .createdAt(LocalDateTime.now())
                .isEnabled(true)
                .isLocked(false)
                .user(user)
                .build();

        authInfoRepository.save(authInfo);

        return jwtService.generateToken(authInfo);
    }
    public UserEntity getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("No authenticated user found.");
        }

        String email = authentication.getName();

        AuthInfoEntity authInfo = authInfoRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return authInfo.getUser();
    }
}
