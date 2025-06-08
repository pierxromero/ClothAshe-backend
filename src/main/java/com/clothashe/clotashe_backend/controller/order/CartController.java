package com.clothashe.clotashe_backend.controller.order;

import com.clothashe.clotashe_backend.model.dto.cart.create.CreateCartItemRequestDTO;
import com.clothashe.clotashe_backend.model.dto.cart.response.CartItemResponseDTO;
import com.clothashe.clotashe_backend.model.dto.cart.response.CartResponseDTO;
import com.clothashe.clotashe_backend.model.dto.cart.update.UpdateCartItemRequestDTO;
import com.clothashe.clotashe_backend.service.order.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Validated
public class CartController {

    private final CartService cartService;

    @GetMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<CartResponseDTO>  viewCart() {
        CartResponseDTO cart = cartService.getActiveCartForUser();
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/items")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<CartItemResponseDTO> addItemToCart(
            @Valid @RequestBody CreateCartItemRequestDTO dto) {
        CartItemResponseDTO addedItem = cartService.addItemToCart(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedItem);
    }

    @PutMapping("/items/{cartItemId}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<CartItemResponseDTO> updateCartItem(
            @PathVariable Long cartItemId,
            @Valid @RequestBody UpdateCartItemRequestDTO dto) {
        CartItemResponseDTO updatedItem = cartService.updateCartItem(cartItemId, dto);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/items/{cartItemId}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable Long cartItemId) {
        cartService.removeItemFromCart(cartItemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clear")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Void> clearCart() {
        cartService.clearCart();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/subtotal")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<BigDecimal> calculateSubtotal() {
        BigDecimal subtotal = cartService.calculateCartSubtotal();
        return ResponseEntity.ok(subtotal);
    }
}