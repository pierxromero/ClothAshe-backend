package com.clothashe.clotashe_backend.mapper.product;

import com.clothashe.clotashe_backend.model.dto.product.create.CreateSizeRequestDTO;
import com.clothashe.clotashe_backend.model.dto.product.response.SizeResponseDTO;
import com.clothashe.clotashe_backend.model.dto.product.update.UpdateSizeRequestDTO;
import com.clothashe.clotashe_backend.model.entity.product.SizeEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface SizeMapper {

    SizeResponseDTO toDto(SizeEntity entity);

    SizeEntity toEntity(CreateSizeRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UpdateSizeRequestDTO dto, @MappingTarget SizeEntity entity);
}