package com.clothashe.clotashe_backend.mapper.product;

import com.clothashe.clotashe_backend.model.dto.product.BrandDTO;
import com.clothashe.clotashe_backend.model.entity.product.BrandEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    BrandDTO toDto(BrandEntity entity);

    BrandEntity toEntity(BrandDTO dto);
}