package com.clothashe.clotashe_backend.mapper.product;

import com.clothashe.clotashe_backend.model.dto.product.ProductDTO;
import com.clothashe.clotashe_backend.model.entity.product.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toDto(ProductEntity entity);

    ProductEntity toEntity(ProductDTO dto);
}