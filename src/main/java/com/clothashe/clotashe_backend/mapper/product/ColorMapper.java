package com.clothashe.clotashe_backend.mapper.product;

import com.clothashe.clotashe_backend.model.dto.product.ColorDTO;
import com.clothashe.clotashe_backend.model.entity.product.ColorEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ColorMapper {

    ColorDTO toDto(ColorEntity entity);

    ColorEntity toEntity(ColorDTO dto);
}