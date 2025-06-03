package com.clothashe.clotashe_backend.mapper.product;

import com.clothashe.clotashe_backend.model.dto.product.create.CreateCategoryRequestDTO;
import com.clothashe.clotashe_backend.model.dto.product.response.CategoryResponseDTO;
import com.clothashe.clotashe_backend.model.dto.product.update.UpdateCategoryRequestDTO;
import com.clothashe.clotashe_backend.model.entity.product.CategoryEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponseDTO toDto(CategoryEntity entity);

    CategoryEntity toEntity(CreateCategoryRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UpdateCategoryRequestDTO dto, @MappingTarget CategoryEntity entity);
}