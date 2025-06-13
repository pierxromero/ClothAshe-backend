package com.clothashe.clotashe_backend.service.order;

import com.clothashe.clotashe_backend.model.dto.order.create.CreateOrderRequestDTO;
import com.clothashe.clotashe_backend.model.dto.order.create.CreatePaymentRequestDTO;
import com.clothashe.clotashe_backend.model.dto.order.response.OrderResponseDTO;
import com.clothashe.clotashe_backend.model.dto.order.response.PaymentResponseDTO;
import com.clothashe.clotashe_backend.model.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    OrderResponseDTO createOrder(CreateOrderRequestDTO dto);

    PaymentResponseDTO createPayment(CreatePaymentRequestDTO dto);

    List<OrderResponseDTO> listUserOrders();

    OrderResponseDTO getOrderById(Long orderId);

    OrderResponseDTO returnOrder(Long orderId);



    Page<OrderResponseDTO> listAllOrders(OrderStatus status, Long userId, Pageable pageable);


    OrderResponseDTO updateOrderStatus(Long orderId, OrderStatus newStatus);


    void cancelOrder(Long orderId);
}