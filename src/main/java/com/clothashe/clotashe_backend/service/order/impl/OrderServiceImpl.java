package com.clothashe.clotashe_backend.service.order.impl;

import com.clothashe.clotashe_backend.exception.ResourceNotFoundException;
import com.clothashe.clotashe_backend.mapper.order.OrderMapper;
import com.clothashe.clotashe_backend.model.dto.order.OrderDTO;
import com.clothashe.clotashe_backend.model.entity.order.OrderEntity;
import com.clothashe.clotashe_backend.repository.order.OrderRepository;
import com.clothashe.clotashe_backend.service.order.OrderService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderDTO create(OrderDTO dto) {
        OrderEntity entity = orderMapper.toEntity(dto);
        return orderMapper.toDto(orderRepository.save(entity));
    }

    @Override
    public OrderDTO update(Long id, OrderDTO dto) {
        OrderEntity existing = orderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        OrderEntity updated = orderMapper.toEntity(dto);
        updated.setId(id);
        return orderMapper.toDto(orderRepository.save(updated));
    }

    @Override
    public OrderDTO getById(Long id) {
        return orderRepository.findById(id)
            .map(orderMapper::toDto)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    @Override
    public List<OrderDTO> getAll() {
        return orderRepository.findAll()
            .stream()
            .map(orderMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }
}