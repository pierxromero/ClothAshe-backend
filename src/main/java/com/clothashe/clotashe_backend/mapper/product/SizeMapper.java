package com.clothashe.clotashe_backend.mapper.product;

import com.clothashe.clotashe_backend.model.dto.product.SizeDTO;
import com.clothashe.clotashe_backend.model.entity.product.SizeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SizeMapper {

    SizeDTO toDto(SizeEntity entity);

    SizeEntity toEntity(SizeDTO dto);
}