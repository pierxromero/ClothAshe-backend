package com.clothashe.clotashe_backend.mapper.order;

import com.clothashe.clotashe_backend.model.dto.cart.response.CartResponseDTO;
import com.clothashe.clotashe_backend.model.entity.cart.CartEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {

    CartResponseDTO toDto(CartEntity entity);
}