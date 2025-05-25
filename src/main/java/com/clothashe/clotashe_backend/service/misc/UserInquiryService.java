package com.clothashe.clotashe_backend.service.misc;

import com.clothashe.clotashe_backend.model.dto.user.UserInquiryDTO;

import java.util.List;

public interface UserInquiryService {
    UserInquiryDTO create(UserInquiryDTO dto);

    UserInquiryDTO update(Long id, UserInquiryDTO dto);

    UserInquiryDTO getById(Long id);

    List<UserInquiryDTO> getAll();

    void delete(Long id);
}