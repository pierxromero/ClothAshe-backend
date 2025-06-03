package com.clothashe.clotashe_backend.service.order.impl;

import com.clothashe.clotashe_backend.exception.ResourceNotFoundException;
import com.clothashe.clotashe_backend.mapper.order.CartItemMapper;
import com.clothashe.clotashe_backend.mapper.order.CartMapper;
import com.clothashe.clotashe_backend.model.dto.cart.create.CreateCartItemRequestDTO;
import com.clothashe.clotashe_backend.model.dto.cart.response.CartItemResponseDTO;
import com.clothashe.clotashe_backend.model.dto.cart.response.CartResponseDTO;
import com.clothashe.clotashe_backend.model.dto.cart.update.UpdateCartItemRequestDTO;
import com.clothashe.clotashe_backend.model.entity.cart.CartEntity;
import com.clothashe.clotashe_backend.model.entity.cart.CartItemEntity;
import com.clothashe.clotashe_backend.model.entity.product.ProductEntity;
import com.clothashe.clotashe_backend.model.entity.user.UserEntity;
import com.clothashe.clotashe_backend.repository.order.CartItemRepository;
import com.clothashe.clotashe_backend.repository.order.CartRepository;
import com.clothashe.clotashe_backend.repository.product.ProductRepository;
import com.clothashe.clotashe_backend.service.auth.impl.AuthService;
import com.clothashe.clotashe_backend.service.order.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final AuthService authService;
    private final CartMapper cartMapper;
    private final CartItemMapper cartItemMapper;

    @Override
    @Transactional(readOnly = true)
    public CartResponseDTO getActiveCartForUser() {
        UserEntity user = authService.getAuthenticatedUser();

        CartEntity cart = cartRepository
                .findByUserIdAndActiveTrue(user.getId())
                .orElseGet(() -> {
                    CartEntity nuevo = CartEntity.builder()
                            .user(user)
                            .createdDate(LocalDateTime.now())
                            .active(true)
                            .build();
                    return cartRepository.save(nuevo);
                });
        return cartMapper.toDto(cart);
    }

    @Override
    public CartItemResponseDTO addItemToCart(CreateCartItemRequestDTO dto) {
        UserEntity user = authService.getAuthenticatedUser();

        CartEntity cart = cartRepository
                .findByUserIdAndActiveTrue(user.getId())
                .orElseGet(() -> {
                    CartEntity nuevo = CartEntity.builder()
                            .user(user)
                            .createdDate(LocalDateTime.now())
                            .active(true)
                            .build();
                    return cartRepository.save(nuevo);
                });

        ProductEntity product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        BigDecimal precioUnitario = product.getPrice();

        CartItemEntity itemExistente = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), product.getId())
                .orElse(null);

        if (itemExistente != null) {
            int nuevaCantidad = itemExistente.getQuantity() + dto.getQuantity();
            itemExistente.setQuantity(nuevaCantidad);
            CartItemEntity actualizado = cartItemRepository.save(itemExistente);
            return cartItemMapper.toDto(actualizado);
        } else {
            CartItemEntity nuevoItem = CartItemEntity.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(dto.getQuantity())
                    .unitPrice(precioUnitario)
                    .build();

            CartItemEntity guardado = cartItemRepository.save(nuevoItem);
            return cartItemMapper.toDto(guardado);
        }
    }


    @Override
    public CartItemResponseDTO updateCartItem(Long cartItemId, UpdateCartItemRequestDTO dto) {
        UserEntity user = authService.getAuthenticatedUser();

        CartItemEntity item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        if (!item.getCart().getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You cannot modify an item that is not yours.");
        }

        if (!item.getProduct().getId().equals(dto.getProductId())) {
            throw new IllegalArgumentException("Changing the product of an item is not allowed. Delete it and add a new one.");
        }

        item.setQuantity(dto.getQuantity());
        CartItemEntity actualizado = cartItemRepository.save(item);

        return cartItemMapper.toDto(actualizado);
    }

    @Override
    public void removeItemFromCart(Long cartItemId) {
        UserEntity user = authService.getAuthenticatedUser();

        CartItemEntity item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        if (!item.getCart().getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You cannot delete an item that is not yours.");
        }

        cartItemRepository.delete(item);
    }

    @Override
    public void clearCart() {
        UserEntity user = authService.getAuthenticatedUser();

        CartEntity cart = cartRepository
                .findByUserIdAndActiveTrue(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        cart.getItems().clear();
        cartRepository.save(cart);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateCartSubtotal() {
        UserEntity user = authService.getAuthenticatedUser();

        CartEntity cart = cartRepository
                .findByUserIdAndActiveTrue(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        return cart.getItems().stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}