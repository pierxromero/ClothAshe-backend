package com.clothashe.clotashe_backend.mapper.misc;

import com.clothashe.clotashe_backend.mapper.auth.UserMapper;
import com.clothashe.clotashe_backend.model.dto.user.create.CreateUserInquiryRequestDTO;
import com.clothashe.clotashe_backend.model.dto.user.response.UserInquiryResponseDTO;
import com.clothashe.clotashe_backend.model.entity.user.UserInquiryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface UserInquiryMapper {

    UserInquiryEntity toEntity(CreateUserInquiryRequestDTO dto);

    UserInquiryResponseDTO toDto(UserInquiryEntity entity);
}