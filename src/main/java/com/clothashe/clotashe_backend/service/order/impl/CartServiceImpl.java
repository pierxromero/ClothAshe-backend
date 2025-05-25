package com.clothashe.clotashe_backend.service.order.impl;

import com.clothashe.clotashe_backend.exception.ResourceNotFoundException;
import com.clothashe.clotashe_backend.mapper.order.CartMapper;
import com.clothashe.clotashe_backend.model.dto.cart.CartDTO;
import com.clothashe.clotashe_backend.model.entity.cart.CartEntity;
import com.clothashe.clotashe_backend.repository.order.CartRepository;
import com.clothashe.clotashe_backend.service.order.CartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    public CartServiceImpl(CartRepository cartRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
    }

    @Override
    public CartDTO create(CartDTO dto) {
        CartEntity entity = cartMapper.toEntity(dto);
        return cartMapper.toDto(cartRepository.save(entity));
    }

    @Override
    public CartDTO update(Long id, CartDTO dto) {
        CartEntity existing = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + id));
        CartEntity updated = cartMapper.toEntity(dto);
        updated.setId(id);
        return cartMapper.toDto(cartRepository.save(updated));
    }

    @Override
    public CartDTO getById(Long id) {
        return cartRepository.findById(id)
                .map(cartMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + id));
    }

    @Override
    public List<CartDTO> getAll() {
        return cartRepository.findAll()
                .stream()
                .map(cartMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!cartRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cart not found with id: " + id);
        }
        cartRepository.deleteById(id);
    }
}