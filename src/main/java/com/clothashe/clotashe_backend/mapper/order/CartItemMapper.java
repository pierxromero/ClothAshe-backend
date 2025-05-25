package com.clothashe.clotashe_backend.mapper.order;

import com.clothashe.clotashe_backend.model.dto.cart.CartItemDTO;
import com.clothashe.clotashe_backend.model.entity.cart.CartItemEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    CartItemDTO toDto(CartItemEntity entity);

    CartItemEntity toEntity(CartItemDTO dto);
}