package com.clothashe.clotashe_backend.service.auth.impl;

import com.clothashe.clotashe_backend.security.JwtService;
import com.clothashe.clotashe_backend.model.dto.auth.RegisterRequest;
import com.clothashe.clotashe_backend.model.entity.auth.AuthInfoEntity;
import com.clothashe.clotashe_backend.model.entity.cart.CartEntity;
import com.clothashe.clotashe_backend.model.entity.user.UserEntity;
import com.clothashe.clotashe_backend.repository.auth.AuthInfoRepository;
import com.clothashe.clotashe_backend.repository.auth.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final AuthInfoRepository authInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public String register(RegisterRequest request) {
        UserEntity user = UserEntity.builder()
                .fullName(request.getFirstName() + " " + request.getLastName())
                .email(request.getEmail())
                .numberPhone(request.getNumberPhone())
                .role(request.getRole())
                .purchaseHistory(new ArrayList<>())
                .favoriteProducts(new ArrayList<>())
                .addresses(new ArrayList<>())
                .build();
        CartEntity cart = new CartEntity();
        cart.setActive(true);
        cart.setCreatedDate(LocalDateTime.now());
        cart.setUser(user);
        user.setCart(cart);
        userRepository.save(user);
       //Manejas excepciones
        AuthInfoEntity authInfo = AuthInfoEntity.builder()
                .username(request.getUsername())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .createdAt(LocalDateTime.now())
                .isEnabled(true)
                .isLocked(false)
                .user(user)
                .build();
        authInfoRepository.save(authInfo);

        return jwtService.generateToken(authInfo);
    }
}
