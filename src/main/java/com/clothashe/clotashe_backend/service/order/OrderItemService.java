package com.clothashe.clotashe_backend.service.order;

import com.clothashe.clotashe_backend.model.dto.order.OrderItemDTO;

import java.util.List;

public interface OrderItemService {
    OrderItemDTO create(OrderItemDTO dto);

    OrderItemDTO update(Long id, OrderItemDTO dto);

    OrderItemDTO getById(Long id);

    List<OrderItemDTO> getAll();

    void delete(Long id);
}