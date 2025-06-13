package com.clothashe.clotashe_backend.mapper.product;

import com.clothashe.clotashe_backend.model.dto.product.create.CreateBrandRequestDTO;
import com.clothashe.clotashe_backend.model.dto.product.response.BrandResponseDTO;
import com.clothashe.clotashe_backend.model.dto.product.update.UpdateBrandRequestDTO;
import com.clothashe.clotashe_backend.model.entity.product.BrandEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BrandMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    BrandEntity toEntity(CreateBrandRequestDTO dto);

    BrandResponseDTO toDto(BrandEntity entity);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    void updateEntityFromDto(UpdateBrandRequestDTO dto, @MappingTarget BrandEntity entity);
}