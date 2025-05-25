package com.clothashe.clotashe_backend.mapper.order;

import com.clothashe.clotashe_backend.model.dto.order.OrderItemDTO;
import com.clothashe.clotashe_backend.model.entity.order.OrderItemEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    OrderItemDTO toDto(OrderItemEntity entity);

    OrderItemEntity toEntity(OrderItemDTO dto);
}