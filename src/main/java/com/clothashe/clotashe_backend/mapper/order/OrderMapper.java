package com.clothashe.clotashe_backend.mapper.order;

import com.clothashe.clotashe_backend.mapper.misc.AddressMapper;
import com.clothashe.clotashe_backend.model.dto.order.create.CreateOrderRequestDTO;
import com.clothashe.clotashe_backend.model.dto.order.response.OrderResponseDTO;
import com.clothashe.clotashe_backend.model.entity.order.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { AddressMapper.class, OrderItemMapper.class })
public interface OrderMapper {
    OrderEntity toEntity(CreateOrderRequestDTO dto);
    OrderResponseDTO toDto(OrderEntity entity);
}