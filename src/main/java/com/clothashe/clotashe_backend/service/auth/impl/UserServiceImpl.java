package com.clothashe.clotashe_backend.service.auth.impl;

import com.clothashe.clotashe_backend.model.dto.user.create.CreateUserRequestDTO;
import com.clothashe.clotashe_backend.model.entity.cart.CartEntity;
import com.clothashe.clotashe_backend.repository.auth.UserRepository;
import com.clothashe.clotashe_backend.service.auth.UserService;
import com.clothashe.clotashe_backend.mapper.auth.UserMapper;
import com.clothashe.clotashe_backend.model.entity.user.UserEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserEntity createUser(CreateUserRequestDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        if (userRepository.existsByNumberPhone(dto.getNumberPhone())) {
            throw new IllegalArgumentException("Phone number already exists");
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