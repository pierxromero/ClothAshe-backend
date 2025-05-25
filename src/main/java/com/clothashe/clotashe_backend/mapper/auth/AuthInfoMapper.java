package com.clothashe.clotashe_backend.mapper.auth;

import com.clothashe.clotashe_backend.model.dto.auth.AuthInfoDTO;
import com.clothashe.clotashe_backend.model.entity.auth.AuthInfoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthInfoMapper {

    AuthInfoDTO toDto(AuthInfoEntity entity);

    AuthInfoEntity toEntity(AuthInfoDTO dto);
}