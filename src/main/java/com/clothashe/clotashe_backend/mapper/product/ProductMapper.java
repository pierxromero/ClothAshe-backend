package com.clothashe.clotashe_backend.mapper.product;

import com.clothashe.clotashe_backend.model.dto.product.create.CreateProductRequestDTO;
import com.clothashe.clotashe_backend.model.dto.product.response.ProductResponseDTO;
import com.clothashe.clotashe_backend.model.dto.product.update.UpdateProductRequestDTO;
import com.clothashe.clotashe_backend.model.entity.product.*;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductEntity toEntity(CreateProductRequestDTO dto);

    ProductResponseDTO toDto(ProductEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UpdateProductRequestDTO dto, @MappingTarget ProductEntity entity);

}