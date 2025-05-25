package com.clothashe.clotashe_backend.mapper.misc;

import com.clothashe.clotashe_backend.model.dto.user.UserInquiryDTO;
import com.clothashe.clotashe_backend.model.entity.user.UserInquiryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserInquiryMapper {

    UserInquiryDTO toDto(UserInquiryEntity entity);

    UserInquiryEntity toEntity(UserInquiryDTO dto);
}