package com.clothashe.clotashe_backend.controller.order;
import com.clothashe.clotashe_backend.model.dto.order.create.CreateOrderRequestDTO;
import com.clothashe.clotashe_backend.model.dto.order.create.CreatePaymentRequestDTO;
import com.clothashe.clotashe_backend.model.dto.order.response.OrderResponseDTO;
import com.clothashe.clotashe_backend.model.dto.order.response.PaymentResponseDTO;
import com.clothashe.clotashe_backend.model.enums.OrderStatus;
import com.clothashe.clotashe_backend.service.order.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<OrderResponseDTO> createOrder(
            @Valid @RequestBody CreateOrderRequestDTO dto) {
        OrderResponseDTO created = orderService.createOrder(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/payment")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<PaymentResponseDTO> createPayment(
            @Valid @RequestBody CreatePaymentRequestDTO dto) {
        PaymentResponseDTO payment = orderService.createPayment(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<OrderResponseDTO>> listUserOrders() {
        List<OrderResponseDTO> list = orderService.listUserOrders();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrderById(
            @PathVariable @Min(1) Long orderId) {
        OrderResponseDTO dto = orderService.getOrderById(orderId);
        return ResponseEntity.ok(dto);
    }


    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<OrderResponseDTO>> listAllOrders(
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) @Min(1) Long userId,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size) {

        Page<OrderResponseDTO> result = orderService.listAllOrders(status, userId, page, size);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{orderId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(
            @PathVariable @Min(1) Long orderId,
            @RequestParam OrderStatus newStatus) {
        OrderResponseDTO updated = orderService.updateOrderStatus(orderId, newStatus);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(
            @PathVariable @Min(1) Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{orderId}/return")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<OrderResponseDTO> returnOrder(
            @PathVariable @Min(1) Long orderId) {
        OrderResponseDTO returned = orderService.returnOrder(orderId);
        return ResponseEntity.ok(returned);
    }
}