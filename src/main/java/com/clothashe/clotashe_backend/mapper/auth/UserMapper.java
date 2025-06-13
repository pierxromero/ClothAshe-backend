package com.clothashe.clotashe_backend.mapper.auth;
import com.clothashe.clotashe_backend.model.dto.user.create.CreateUserRequestDTO;
import com.clothashe.clotashe_backend.model.dto.user.response.*;
import com.clothashe.clotashe_backend.model.dto.user.update.UpdateUserDTO;
import com.clothashe.clotashe_backend.model.entity.user.UserEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")

public interface UserMapper {

    UserEntity toEntity(CreateUserRequestDTO dto);

    UserResponseDTO toDto(UserEntity entity);

    UserDTO toUserDTO(UserEntity entity);

    List<UserDTO> toDTOs(List<UserEntity> entities);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(UpdateUserDTO dto, @MappingTarget UserEntity entity);
}