package com.clothashe.clotashe_backend.service.order;

import com.clothashe.clotashe_backend.model.dto.cart.CartItemDTO;

import java.util.List;

public interface CartItemService {
    CartItemDTO create(CartItemDTO dto);

    CartItemDTO update(Long id, CartItemDTO dto);

    CartItemDTO getById(Long id);

    List<CartItemDTO> getAll();

    void delete(Long id);
}