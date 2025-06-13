package com.clothashe.clotashe_backend.service.misc;

import com.clothashe.clotashe_backend.model.dto.user.create.AnswerInquiryRequestDTO;
import com.clothashe.clotashe_backend.model.dto.user.create.CreateUserInquiryRequestDTO;
import com.clothashe.clotashe_backend.model.dto.user.response.UserInquiryResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserInquiryService {

    UserInquiryResponseDTO createInquiry(CreateUserInquiryRequestDTO dto);

    Page<UserInquiryResponseDTO> listAllInquiries(Boolean answered, Long userId, Pageable pageable);

    List<UserInquiryResponseDTO> listUserInquiries();

    UserInquiryResponseDTO getInquiryById(Long inquiryId);

    UserInquiryResponseDTO answerInquiry(AnswerInquiryRequestDTO dto);

    void deleteInquiry(Long inquiryId);
}