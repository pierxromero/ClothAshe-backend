package com.clothashe.clotashe_backend.service.order;

import com.clothashe.clotashe_backend.model.dto.order.OrderDTO;

import java.util.List;

public interface OrderService {
    OrderDTO create(OrderDTO dto);

    OrderDTO update(Long id, OrderDTO dto);

    OrderDTO getById(Long id);

    List<OrderDTO> getAll();

    void delete(Long id);
}