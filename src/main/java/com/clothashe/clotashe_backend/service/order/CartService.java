package com.clothashe.clotashe_backend.service.order;

import com.clothashe.clotashe_backend.model.dto.cart.create.CreateCartItemRequestDTO;
import com.clothashe.clotashe_backend.model.dto.cart.response.CartItemResponseDTO;
import com.clothashe.clotashe_backend.model.dto.cart.response.CartResponseDTO;
import com.clothashe.clotashe_backend.model.dto.cart.update.UpdateCartItemRequestDTO;

import java.math.BigDecimal;

public interface CartService {

    CartResponseDTO getActiveCartForUser();

    CartItemResponseDTO addItemToCart(CreateCartItemRequestDTO dto);

    CartItemResponseDTO updateCartItem(Long cartItemId, UpdateCartItemRequestDTO dto);

    void removeItemFromCart(Long cartItemId);

    void clearCart();

    BigDecimal calculateCartSubtotal();
}