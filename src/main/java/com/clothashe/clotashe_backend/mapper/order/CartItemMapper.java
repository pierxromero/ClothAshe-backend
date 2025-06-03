package com.clothashe.clotashe_backend.mapper.order;

import com.clothashe.clotashe_backend.model.dto.cart.create.CreateCartItemRequestDTO;
import com.clothashe.clotashe_backend.model.dto.cart.response.CartItemResponseDTO;
import com.clothashe.clotashe_backend.model.dto.cart.update.UpdateCartItemRequestDTO;
import com.clothashe.clotashe_backend.model.entity.cart.CartItemEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    CartItemResponseDTO toDto(CartItemEntity entity);

    CartItemEntity toEntity(CreateCartItemRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UpdateCartItemRequestDTO dto, @MappingTarget CartItemEntity entity);
}