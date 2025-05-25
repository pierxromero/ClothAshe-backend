package com.clothashe.clotashe_backend.service.order.impl;

import com.clothashe.clotashe_backend.exception.ResourceNotFoundException;
import com.clothashe.clotashe_backend.mapper.order.OrderItemMapper;
import com.clothashe.clotashe_backend.model.dto.order.OrderItemDTO;
import com.clothashe.clotashe_backend.model.entity.order.OrderItemEntity;
import com.clothashe.clotashe_backend.repository.order.OrderItemRepository;
import com.clothashe.clotashe_backend.service.order.OrderItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, OrderItemMapper orderItemMapper) {
        this.orderItemRepository = orderItemRepository;
        this.orderItemMapper = orderItemMapper;
    }

    @Override
    public OrderItemDTO create(OrderItemDTO dto) {
        OrderItemEntity entity = orderItemMapper.toEntity(dto);
        return orderItemMapper.toDto(orderItemRepository.save(entity));
    }

    @Override
    public OrderItemDTO update(Long id, OrderItemDTO dto) {
        OrderItemEntity existing = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem not found with id: " + id));
        OrderItemEntity updated = orderItemMapper.toEntity(dto);
        updated.setId(id);
        return orderItemMapper.toDto(orderItemRepository.save(updated));
    }

    @Override
    public OrderItemDTO getById(Long id) {
        return orderItemRepository.findById(id)
                .map(orderItemMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem not found with id: " + id));
    }

    @Override
    public List<OrderItemDTO> getAll() {
        return orderItemRepository.findAll()
                .stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!orderItemRepository.existsById(id)) {
            throw new ResourceNotFoundException("OrderItem not found with id: " + id);
        }
        orderItemRepository.deleteById(id);
    }
}