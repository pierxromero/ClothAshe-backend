package com.clothashe.clotashe_backend.mapper.auth;

import com.clothashe.clotashe_backend.model.dto.user.UserDTO;
import com.clothashe.clotashe_backend.model.entity.user.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDto(UserEntity entity);

    UserEntity toEntity(UserDTO dto);
}