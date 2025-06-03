package com.clothashe.clotashe_backend.mapper.product;

import com.clothashe.clotashe_backend.model.dto.product.create.CreateColorRequestDTO;
import com.clothashe.clotashe_backend.model.dto.product.response.ColorResponseDTO;
import com.clothashe.clotashe_backend.model.dto.product.update.UpdateColorRequestDTO;
import com.clothashe.clotashe_backend.model.entity.product.ColorEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ColorMapper {

    ColorResponseDTO toDto(ColorEntity entity);

    ColorEntity toEntity(CreateColorRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UpdateColorRequestDTO dto, @MappingTarget ColorEntity entity);
}