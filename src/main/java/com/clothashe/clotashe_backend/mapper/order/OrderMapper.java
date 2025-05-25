package com.clothashe.clotashe_backend.mapper.order;

import com.clothashe.clotashe_backend.model.dto.order.OrderDTO;
import com.clothashe.clotashe_backend.model.entity.order.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDTO toDto(OrderEntity entity);

    OrderEntity toEntity(OrderDTO dto);
}