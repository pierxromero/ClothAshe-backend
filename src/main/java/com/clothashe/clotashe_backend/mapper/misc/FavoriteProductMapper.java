package com.clothashe.clotashe_backend.mapper.misc;

import com.clothashe.clotashe_backend.model.dto.user.create.CreateFavoriteProductRequestDTO;
import com.clothashe.clotashe_backend.model.dto.user.response.FavoriteProductResponseDTO;
import com.clothashe.clotashe_backend.model.entity.user.FavoriteProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FavoriteProductMapper {

    FavoriteProductResponseDTO toDto(FavoriteProductEntity entity);

    FavoriteProductEntity toEntity(CreateFavoriteProductRequestDTO dto);
}