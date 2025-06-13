package com.clothashe.clotashe_backend.service.auth.impl;

import com.clothashe.clotashe_backend.exception.users.EmailAlreadyExistsException;
import com.clothashe.clotashe_backend.exception.users.PhoneAlreadyExistsException;
import com.clothashe.clotashe_backend.exception.users.UserAccessDeniedException;
import com.clothashe.clotashe_backend.exception.users.UserNotFoundException;
import com.clothashe.clotashe_backend.mapper.auth.UserMapper;
import com.clothashe.clotashe_backend.model.dto.auth.LoginRequest;
import com.clothashe.clotashe_backend.model.dto.user.create.CreateUserRequestDTO;
import com.clothashe.clotashe_backend.model.entity.cart.CartEntity;
import com.clothashe.clotashe_backend.model.enums.Role;
import com.clothashe.clotashe_backend.repository.auth.UserRepository;
import com.clothashe.clotashe_backend.security.JwtService;
import com.clothashe.clotashe_backend.model.dto.auth.RegisterRequest;
import com.clothashe.clotashe_backend.model.entity.auth.AuthInfoEntity;
import com.clothashe.clotashe_backend.model.entity.user.UserEntity;
import com.clothashe.clotashe_backend.repository.auth.AuthInfoRepository;
import com.clothashe.clotashe_backend.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final AuthInfoRepository authInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public String login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        AuthInfoEntity user = (AuthInfoEntity) authentication.getPrincipal();
        return jwtService.generateToken(user);
    }

    @Override
    @Transactional
    public String register(RegisterRequest request) {

        if (authInfoRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        CreateUserRequestDTO userDto = CreateUserRequestDTO.builder()
                .fullName(request.getFirstName() + " " + request.getLastName())
                .email(request.getEmail())
                .numberPhone(request.getNumberPhone())
                .role(Role.CLIENT)
                .build();

        UserEntity user = createUser(userDto);

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

    @Override
    public UserEntity getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UserAccessDeniedException("No authenticated user found.");
        }

        String email = authentication.getName();

        AuthInfoEntity authInfo = authInfoRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        return authInfo.getUser();
    }

    @Transactional
    public UserEntity createUser(CreateUserRequestDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException(dto.getEmail());
        }

        if (userRepository.existsByNumberPhone(dto.getNumberPhone())) {
            throw new PhoneAlreadyExistsException(dto.getNumberPhone());
        }

        UserEntity user = userMapper.toEntity(dto);
        user.setAddresses(new ArrayList<>());
        user.setFavorites(new ArrayList<>());
        user.setPurchaseHistory(new ArrayList<>());

        CartEntity cart = new CartEntity();
        cart.setActive(true);
        cart.setCreatedDate(LocalDateTime.now());
        cart.setUser(user);
        user.setCart(cart);

        return userRepository.save(user);
    }
}
