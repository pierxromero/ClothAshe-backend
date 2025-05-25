package com.clothashe.clotashe_backend.service.order;

import com.clothashe.clotashe_backend.model.dto.cart.CartDTO;

import java.util.List;

public interface CartService {
    CartDTO create(CartDTO dto);

    CartDTO update(Long id, CartDTO dto);

    CartDTO getById(Long id);

    List<CartDTO> getAll();

    void delete(Long id);
}