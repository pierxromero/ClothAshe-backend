package com.clothashe.clotashe_backend.service.order.impl;

import com.clothashe.clotashe_backend.exception.ResourceNotFoundException;
import com.clothashe.clotashe_backend.mapper.order.CartItemMapper;
import com.clothashe.clotashe_backend.model.dto.cart.CartItemDTO;
import com.clothashe.clotashe_backend.model.entity.cart.CartItemEntity;
import com.clothashe.clotashe_backend.repository.order.CartItemRepository;
import com.clothashe.clotashe_backend.service.order.CartItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, CartItemMapper cartItemMapper) {
        this.cartItemRepository = cartItemRepository;
        this.cartItemMapper = cartItemMapper;
    }

    @Override
    public CartItemDTO create(CartItemDTO dto) {
        CartItemEntity entity = cartItemMapper.toEntity(dto);
        return cartItemMapper.toDto(cartItemRepository.save(entity));
    }

    @Override
    public CartItemDTO update(Long id, CartItemDTO dto) {
        CartItemEntity existing = cartItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem not found with id: " + id));
        CartItemEntity updated = cartItemMapper.toEntity(dto);
        updated.setId(id);
        return cartItemMapper.toDto(cartItemRepository.save(updated));
    }

    @Override
    public CartItemDTO getById(Long id) {
        return cartItemRepository.findById(id)
                .map(cartItemMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem not found with id: " + id));
    }

    @Override
    public List<CartItemDTO> getAll() {
        return cartItemRepository.findAll()
                .stream()
                .map(cartItemMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!cartItemRepository.existsById(id)) {
            throw new ResourceNotFoundException("CartItem not found with id: " + id);
        }
        cartItemRepository.deleteById(id);
    }
}