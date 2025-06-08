package com.clothashe.clotashe_backend.controller.order;
import com.clothashe.clotashe_backend.exception.error.ApiError;
import com.clothashe.clotashe_backend.model.dto.order.create.CreateOrderRequestDTO;
import com.clothashe.clotashe_backend.model.dto.order.create.CreatePaymentRequestDTO;
import com.clothashe.clotashe_backend.model.dto.order.response.OrderResponseDTO;
import com.clothashe.clotashe_backend.model.dto.order.response.PaymentResponseDTO;
import com.clothashe.clotashe_backend.model.enums.OrderStatus;
import com.clothashe.clotashe_backend.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Validated
@Tag(name = "Orders", description = "Operations related to order processing")
public class OrderController {

    private final OrderService orderService;


    @Operation(
            summary = "Create a new order",
            description = "Allows a client to create an order based on their active cart and a shipping address.",
            security = @SecurityRequirement(name = "bearerAuth"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Order details including shipping address ID and payment method",
                    content = @Content(schema = @Schema(implementation = CreateOrderRequestDTO.class))
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order created successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = OrderResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "403", description = "Access denied: invalid address or not client role"),
            @ApiResponse(responseCode = "404", description = "Resource not found (address or cart)")
    })
    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(
            @Valid @RequestBody CreateOrderRequestDTO dto
    ) {
        OrderResponseDTO created = orderService.createOrder(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(
            summary = "Create a payment for an order",
            description = "Allows a client to pay for an existing order in PENDING status.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Payment recorded successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaymentResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid payment data or amount mismatch"),
            @ApiResponse(responseCode = "403", description = "Access denied: not order owner or invalid status"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/payment")
    public ResponseEntity<PaymentResponseDTO> createPayment(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Payment details including order ID and amount",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CreatePaymentRequestDTO.class))
            )
            @Valid @org.springframework.web.bind.annotation.RequestBody CreatePaymentRequestDTO dto
    ) {
        PaymentResponseDTO payment = orderService.createPayment(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    }

    @Operation(
            summary = "List orders of the authenticated user",
            description = "Retrieves all orders placed by the current client.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "List of user orders",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = OrderResponseDTO.class))))
    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/me")
    public ResponseEntity<List<OrderResponseDTO>> listUserOrders() {
        List<OrderResponseDTO> list = orderService.listUserOrders();
        return ResponseEntity.ok(list);
    }

    @Operation(
            summary = "Get order by ID",
            description = "Retrieves a specific order by its ID if the requester is the owner or an admin.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = OrderResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Access denied: not owner or admin"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrderById(
            @Parameter(description = "ID of the order to retrieve", required = true, in = ParameterIn.PATH)
            @PathVariable @Min(1) Long orderId
    ) {
        OrderResponseDTO dto = orderService.getOrderById(orderId);
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "List all orders (admin only)",
            description = "Allows an admin to list all orders, with optional filtering by status and/or user ID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page of orders",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "403", description = "Access denied: not admin")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<Page<OrderResponseDTO>> listAllOrders(
            @Parameter(description = "Filter by order status", required = false)
            @RequestParam(required = false) OrderStatus status,
            @Parameter(description = "Filter by user ID", required = false)
            @RequestParam(required = false) @Min(1) Long userId,
            @Parameter(description = "Page number (zero-based)")
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @Parameter(description = "Page size")
            @RequestParam(defaultValue = "10") @Min(1) int size
    ) {
        Page<OrderResponseDTO> result = orderService.listAllOrders(status, userId, page, size);
        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "Update order status (admin only)",
            description = "Allows an admin to update the status of an order.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order status updated",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = OrderResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid status change"),
            @ApiResponse(responseCode = "403", description = "Access denied: not admin"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(
            @Parameter(description = "ID of the order to update", required = true, in = ParameterIn.PATH)
            @PathVariable @Min(1) Long orderId,
            @Parameter(description = "New status for the order", required = true)
            @RequestParam OrderStatus newStatus
    ) {
        OrderResponseDTO updated = orderService.updateOrderStatus(orderId, newStatus);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Cancel an order",
            description = "Allows the owner or an admin to cancel an order in PENDING status.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Order cancelled successfully"),
            @ApiResponse(responseCode = "400", description = "Only PENDING orders can be cancelled"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @DeleteMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(
            @Parameter(description = "ID of the order to cancel", required = true, in = ParameterIn.PATH)
            @PathVariable @Min(1) Long orderId
    ) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Return an order",
            description = "Allows the owner to return an order that has been delivered.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order returned successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = OrderResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Only DELIVERED orders can be returned"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/{orderId}/return")
    public ResponseEntity<OrderResponseDTO> returnOrder(
            @Parameter(description = "ID of the order to return", required = true, in = ParameterIn.PATH)
            @PathVariable @Min(1) Long orderId
    ) {
        OrderResponseDTO returned = orderService.returnOrder(orderId);
        return ResponseEntity.ok(returned);
    }
}