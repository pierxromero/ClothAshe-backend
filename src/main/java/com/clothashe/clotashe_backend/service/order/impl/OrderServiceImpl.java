package com.clothashe.clotashe_backend.service.order.impl;

import com.clothashe.clotashe_backend.exception.misc.ResourceNotFoundException;
import com.clothashe.clotashe_backend.exception.order.*;
import com.clothashe.clotashe_backend.mapper.order.OrderMapper;
import com.clothashe.clotashe_backend.mapper.order.PaymentMapper;
import com.clothashe.clotashe_backend.model.dto.order.create.CreateOrderRequestDTO;
import com.clothashe.clotashe_backend.model.dto.order.create.CreatePaymentRequestDTO;
import com.clothashe.clotashe_backend.model.dto.order.response.OrderResponseDTO;
import com.clothashe.clotashe_backend.model.dto.order.response.PaymentResponseDTO;
import com.clothashe.clotashe_backend.model.entity.cart.CartEntity;
import com.clothashe.clotashe_backend.model.entity.order.OrderEntity;
import com.clothashe.clotashe_backend.model.entity.order.OrderItemEntity;
import com.clothashe.clotashe_backend.model.entity.order.PaymentEntity;
import com.clothashe.clotashe_backend.model.entity.user.AddressEntity;
import com.clothashe.clotashe_backend.model.entity.user.UserEntity;
import com.clothashe.clotashe_backend.model.enums.OrderStatus;
import com.clothashe.clotashe_backend.model.enums.PaymentStatus;
import com.clothashe.clotashe_backend.model.enums.Role;
import com.clothashe.clotashe_backend.repository.misc.AddressRepository;
import com.clothashe.clotashe_backend.repository.order.CartRepository;
import com.clothashe.clotashe_backend.repository.order.OrderRepository;
import com.clothashe.clotashe_backend.repository.order.PaymentRepository;
import com.clothashe.clotashe_backend.service.auth.AuthService;
import com.clothashe.clotashe_backend.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final PaymentRepository paymentRepository;
    private final AuthService authService;
    private final OrderMapper orderMapper;
    private final PaymentMapper paymentMapper;

    @Override
    public OrderResponseDTO createOrder(CreateOrderRequestDTO dto) {
        UserEntity user = authService.getAuthenticatedUser();

        CartEntity cart = cartRepository
                .findByUserIdAndActiveTrue(user.getId())
                .orElseThrow(() -> new EmptyCartException("Cart not found or empty"));

        if (cart.getItems().isEmpty()) {
            throw new EmptyCartException("Cannot create order with empty cart");
        }

        AddressEntity address = addressRepository.findById(dto.getShippingAddressId())
                .orElseThrow(() -> new ResourceNotFoundException("Shipping address not found"));

        if (!address.getUser().getId().equals(user.getId())) {
            throw new OrderAccessDeniedException("No permission to use this address");
        }

        BigDecimal total = cart.getItems().stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        OrderEntity order = OrderEntity.builder()
                .user(user)
                .orderDate(LocalDateTime.now())
                .totalAmount(total)
                .status(OrderStatus.PENDING)
                .paymentMethod(dto.getPaymentMethod())
                .shippingAddress(address)
                .build();

        List<OrderItemEntity> orderItems = cart.getItems().stream()
                .map(cartItem -> {
                    OrderItemEntity oItem = OrderItemEntity.builder()
                            .order(order)
                            .product(cartItem.getProduct())
                            .quantity(cartItem.getQuantity())
                            .unitPrice(cartItem.getUnitPrice())
                            .subtotal(cartItem.getUnitPrice()
                                    .multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                            .build();
                    return oItem;
                })
                .collect(Collectors.toList());

        order.setItems(orderItems);

        OrderEntity savedOrder = orderRepository.save(order);

        cart.getItems().clear();
        cartRepository.save(cart);

        return orderMapper.toDto(savedOrder);
    }

    @Override
    public PaymentResponseDTO createPayment(CreatePaymentRequestDTO dto) {
        UserEntity user = authService.getAuthenticatedUser();

        OrderEntity order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new OrderAccessDeniedException("No permission to pay this order");
        }

        if (!order.getStatus().equals(OrderStatus.PENDING)) {
            throw new PaymentNotAllowedException("Only orders in PENDING state can be paid");
        }

        if (dto.getAmount().compareTo(order.getTotalAmount()) != 0) {
            throw new InvalidPaymentAmountException("Payment amount must match order total");
        }

        PaymentEntity payment = PaymentEntity.builder()
                .order(order)
                .amount(dto.getAmount())
                .paymentDate(LocalDateTime.now())
                .status(PaymentStatus.APPROVED)
                .build();

        PaymentEntity savedPayment = paymentRepository.save(payment);

        order.setStatus(OrderStatus.PAID);
        orderRepository.save(order);

        return paymentMapper.toDto(savedPayment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDTO> listUserOrders() {
        UserEntity user = authService.getAuthenticatedUser();

        List<OrderEntity> orders = orderRepository.findByUserId(user.getId(), Pageable.unpaged()).getContent();

        return orders.stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDTO getOrderById(Long orderId) {
        UserEntity user = authService.getAuthenticatedUser();

        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        if (!order.getUser().getId().equals(user.getId()) && !user.getRole().equals(Role.ADMIN)) {
            throw new OrderAccessDeniedException("No permission to view this order");
        }

        return orderMapper.toDto(order);
    }

    // Only admin

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponseDTO> listAllOrders(OrderStatus status, Long userId, Pageable pageable) {
        Page<OrderEntity> pageResult;

        if (status != null && userId != null) {
            pageResult = orderRepository.findByStatusAndUserId(status, userId, pageable);
        } else if (status != null) {
            pageResult = orderRepository.findByStatus(status, pageable);
        } else if (userId != null) {
            pageResult = orderRepository.findByUserId(userId, pageable);
        } else {
            pageResult = orderRepository.findAll(pageable);
        }

        return pageResult.map(orderMapper::toDto);
    }

    @Override
    public OrderResponseDTO updateOrderStatus(Long orderId, OrderStatus newStatus) {
        UserEntity user = authService.getAuthenticatedUser();

        if (!user.getRole().equals(Role.ADMIN)) {
            throw new OrderAccessDeniedException("Only admin can update order status");
        }

        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        order.setStatus(newStatus);
        OrderEntity updated = orderRepository.save(order);

        return orderMapper.toDto(updated);
    }

    @Override
    public void cancelOrder(Long orderId) {
        UserEntity user = authService.getAuthenticatedUser();

        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        boolean isOwner = order.getUser().getId().equals(user.getId());
        boolean isAdmin = user.getRole().equals(Role.ADMIN);

        if (!isOwner && !isAdmin) {
            throw new OrderAccessDeniedException("No permission to cancel this order");
        }

        if (!order.getStatus().equals(OrderStatus.PENDING)) {
            throw new OrderStatusUpdateNotAllowedException("Only PENDING orders can be cancelled");
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    @Override
    @Transactional
    public OrderResponseDTO returnOrder(Long orderId) {
        UserEntity user = authService.getAuthenticatedUser();

        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new OrderAccessDeniedException("You can only return your own orders");
        }

        if (!order.getStatus().equals(OrderStatus.DELIVERED)) {
            throw new OrderStatusUpdateNotAllowedException("Only delivered orders can be returned");
        }

        order.setStatus(OrderStatus.RETURNED);
        OrderEntity updated = orderRepository.save(order);

        return orderMapper.toDto(updated);
    }
}