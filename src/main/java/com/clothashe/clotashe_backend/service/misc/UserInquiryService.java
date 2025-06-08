package com.clothashe.clotashe_backend.service.misc;

import com.clothashe.clotashe_backend.model.dto.user.create.AnswerInquiryRequestDTO;
import com.clothashe.clotashe_backend.model.dto.user.create.CreateUserInquiryRequestDTO;
import com.clothashe.clotashe_backend.model.dto.user.response.UserInquiryResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserInquiryService {

    UserInquiryResponseDTO createInquiry(CreateUserInquiryRequestDTO dto);

    Page<UserInquiryResponseDTO> listAllInquiries(Boolean answered, Long userId, int page, int size);

    List<UserInquiryResponseDTO> listUserInquiries();

    UserInquiryResponseDTO getInquiryById(Long inquiryId);

    UserInquiryResponseDTO answerInquiry(AnswerInquiryRequestDTO dto);

    void deleteInquiry(Long inquiryId);
}