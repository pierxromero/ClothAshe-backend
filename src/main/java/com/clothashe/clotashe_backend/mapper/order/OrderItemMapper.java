package com.clothashe.clotashe_backend.mapper.order;

import com.clothashe.clotashe_backend.model.dto.order.response.OrderItemResponseDTO;
import com.clothashe.clotashe_backend.model.entity.order.OrderItemEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItemResponseDTO toDto(OrderItemEntity entity);
}