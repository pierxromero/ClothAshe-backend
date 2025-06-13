package com.clothashe.clotashe_backend.mapper.misc;

import com.clothashe.clotashe_backend.model.dto.user.create.CreateAddressRequestDTO;
import com.clothashe.clotashe_backend.model.dto.user.response.AddressResponseDTO;
import com.clothashe.clotashe_backend.model.dto.user.update.UpdateAddressRequestDTO;
import com.clothashe.clotashe_backend.model.entity.user.AddressEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    @Mapping(source = "user.id", target = "userId")
    AddressResponseDTO toDto(AddressEntity entity);

    AddressEntity toEntity(CreateAddressRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(UpdateAddressRequestDTO dto, @MappingTarget AddressEntity entity);
}