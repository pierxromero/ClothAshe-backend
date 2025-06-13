package com.clothashe.clotashe_backend.controller.order;

import com.clothashe.clotashe_backend.exception.error.ApiError;
import com.clothashe.clotashe_backend.model.dto.cart.create.CreateCartItemRequestDTO;
import com.clothashe.clotashe_backend.model.dto.cart.response.CartItemResponseDTO;
import com.clothashe.clotashe_backend.model.dto.cart.response.CartResponseDTO;
import com.clothashe.clotashe_backend.model.dto.cart.update.UpdateCartItemRequestDTO;
import com.clothashe.clotashe_backend.service.order.CartService;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Validated
@Tag(name = "Shopping Cart", description = "Operations related to the user's shopping cart")
public class CartController {

    private final CartService cartService;

    @Operation(
            summary = "Get the current user's active cart",
            description = "Returns the active cart for the authenticated user. If no active cart exists, one is created.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Active cart retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CartResponseDTO.class))
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Authentication required",
                      "status": 401,
                      "errorCode": "UNAUTHORIZED",
                      "path": "/api/cart",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            )
    })
    @GetMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<CartResponseDTO> viewCart() {
        CartResponseDTO cart = cartService.getActiveCartForUser();
        return ResponseEntity.ok(cart);
    }

    @Operation(
            summary = "Add an item to the cart",
            description = "Adds a product to the current user's active cart. If the product is already in the cart, its quantity is increased.",
            security = @SecurityRequirement(name = "bearerAuth"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Product and quantity to add",
                    content = @Content(schema = @Schema(implementation = CreateCartItemRequestDTO.class))
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Item added successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CartItemResponseDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Quantity must be at least 1",
                      "status": 400,
                      "errorCode": "BAD_REQUEST",
                      "path": "/api/cart/items",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Authentication required",
                      "status": 401,
                      "errorCode": "UNAUTHORIZED",
                      "path": "/api/cart/items",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Product with ID 123 not found",
                      "status": 404,
                      "errorCode": "NOT_FOUND",
                      "path": "/api/cart/items",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            )
    })
    @PostMapping("/items")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<CartItemResponseDTO> addItemToCart(
            @Valid @RequestBody CreateCartItemRequestDTO dto) {
        CartItemResponseDTO addedItem = cartService.addItemToCart(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedItem);
    }

    @Operation(
            summary = "Update the quantity of a cart item",
            description = "Updates the quantity of an existing item in the user's cart.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(name = "cartItemId", description = "ID of the cart item to update", required = true)
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "New quantity for the item",
                    content = @Content(schema = @Schema(implementation = UpdateCartItemRequestDTO.class))
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CartItemResponseDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Quantity must be at least 1",
                      "status": 400,
                      "errorCode": "BAD_REQUEST",
                      "path": "/api/cart/items/456",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Authentication required",
                      "status": 401,
                      "errorCode": "UNAUTHORIZED",
                      "path": "/api/cart/items/456",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "403", description = "Forbidden - User does not own the item",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Not authorized to modify this cart item",
                      "status": 403,
                      "errorCode": "FORBIDDEN",
                      "path": "/api/cart/items/456",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Cart item not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Cart item with ID 456 not found",
                      "status": 404,
                      "errorCode": "NOT_FOUND",
                      "path": "/api/cart/items/456",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            )
    })
    @PutMapping("/items/{cartItemId}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<CartItemResponseDTO> updateCartItem(
            @PathVariable Long cartItemId,
            @Valid @RequestBody UpdateCartItemRequestDTO dto) {
        CartItemResponseDTO updatedItem = cartService.updateCartItem(cartItemId, dto);
        return ResponseEntity.ok(updatedItem);
    }

    @Operation(
            summary = "Remove an item from the cart",
            description = "Deletes a specific item from the user's cart.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(name = "cartItemId", description = "ID of the cart item to remove", required = true)
            }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Item removed successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Authentication required",
                      "status": 401,
                      "errorCode": "UNAUTHORIZED",
                      "path": "/api/cart/items/789",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "403", description = "Forbidden - User does not own the item",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Not authorized to delete this cart item",
                      "status": 403,
                      "errorCode": "FORBIDDEN",
                      "path": "/api/cart/items/789",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Cart item not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Cart item with ID 789 not found",
                      "status": 404,
                      "errorCode": "NOT_FOUND",
                      "path": "/api/cart/items/789",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            )
    })
    @DeleteMapping("/items/{cartItemId}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable Long cartItemId) {
        cartService.removeItemFromCart(cartItemId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Clear all items in the cart",
            description = "Removes all items from the current user's active cart.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cart cleared successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Authentication required",
                      "status": 401,
                      "errorCode": "UNAUTHORIZED",
                      "path": "/api/cart/clear",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Cart not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Active cart not found",
                      "status": 404,
                      "errorCode": "NOT_FOUND",
                      "path": "/api/cart/clear",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            )
    })
    @DeleteMapping("/clear")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Void> clearCart() {
        cartService.clearCart();
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Calculate subtotal of the cart",
            description = "Returns the total cost of all items in the current user's cart.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Subtotal calculated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BigDecimal.class))
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Authentication required",
                      "status": 401,
                      "errorCode": "UNAUTHORIZED",
                      "path": "/api/cart/subtotal",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Cart not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Active cart not found",
                      "status": 404,
                      "errorCode": "NOT_FOUND",
                      "path": "/api/cart/subtotal",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            )
    })
    @GetMapping("/subtotal")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<BigDecimal> calculateSubtotal() {
        BigDecimal subtotal = cartService.calculateCartSubtotal();
        return ResponseEntity.ok(subtotal);
    }
}