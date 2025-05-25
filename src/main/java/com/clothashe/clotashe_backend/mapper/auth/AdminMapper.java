package com.clothashe.clotashe_backend.mapper.auth;

import com.clothashe.clotashe_backend.model.dto.admin.AdminDTO;
import com.clothashe.clotashe_backend.model.entity.admin.AdminEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    AdminDTO toDto(AdminEntity entity);

    AdminEntity toEntity(AdminDTO dto);
}