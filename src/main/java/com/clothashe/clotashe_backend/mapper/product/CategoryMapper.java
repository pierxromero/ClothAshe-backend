package com.clothashe.clotashe_backend.mapper.product;

import com.clothashe.clotashe_backend.model.dto.product.CategoryDTO;
import com.clothashe.clotashe_backend.model.entity.product.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDTO toDto(CategoryEntity entity);

    CategoryEntity toEntity(CategoryDTO dto);
}