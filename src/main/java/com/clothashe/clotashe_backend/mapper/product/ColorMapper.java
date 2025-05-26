package com.clothashe.clotashe_backend.mapper.product;

import com.clothashe.clotashe_backend.model.dto.product.ColorDTO;
import com.clothashe.clotashe_backend.model.entity.product.ColorEntity;
import com.clothashe.clotashe_backend.service.product.ColorService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ColorService.class)
public interface ColorMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "hexCode", source = "hexCode")
    ColorDTO toDto(ColorEntity entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "hexCode", source = "hexCode")
    ColorEntity toEntity(ColorDTO dto);
}