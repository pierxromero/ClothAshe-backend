package com.clothashe.clotashe_backend.mapper.misc;

import com.clothashe.clotashe_backend.model.dto.user.AddressDTO;
import com.clothashe.clotashe_backend.model.entity.user.AddressEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressDTO toDto(AddressEntity entity);

    AddressEntity toEntity(AddressDTO dto);
}