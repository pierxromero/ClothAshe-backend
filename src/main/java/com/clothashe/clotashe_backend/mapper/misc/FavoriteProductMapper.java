package com.clothashe.clotashe_backend.mapper.misc;

import com.clothashe.clotashe_backend.model.dto.user.FavoriteProductDTO;
import com.clothashe.clotashe_backend.model.entity.user.FavoriteProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FavoriteProductMapper {

    FavoriteProductDTO toDto(FavoriteProductEntity entity);

    FavoriteProductEntity toEntity(FavoriteProductDTO dto);
}