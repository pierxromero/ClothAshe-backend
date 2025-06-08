package com.clothashe.clotashe_backend.service.misc.impl;

import com.clothashe.clotashe_backend.exception.misc.ResourceNotFoundException;
import com.clothashe.clotashe_backend.exception.misc.InquiryAlreadyAnsweredException;
import com.clothashe.clotashe_backend.exception.users.UserAccessDeniedException;
import com.clothashe.clotashe_backend.mapper.misc.UserInquiryMapper;
import com.clothashe.clotashe_backend.model.dto.user.create.AnswerInquiryRequestDTO;
import com.clothashe.clotashe_backend.model.dto.user.create.CreateUserInquiryRequestDTO;
import com.clothashe.clotashe_backend.model.dto.user.response.UserInquiryResponseDTO;
import com.clothashe.clotashe_backend.model.entity.user.UserEntity;
import com.clothashe.clotashe_backend.model.entity.user.UserInquiryEntity;
import com.clothashe.clotashe_backend.model.enums.Role;
import com.clothashe.clotashe_backend.repository.misc.UserInquiryRepository;
import com.clothashe.clotashe_backend.service.auth.AuthService;
import com.clothashe.clotashe_backend.service.misc.UserInquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserInquiryServiceImpl implements UserInquiryService {

    private final UserInquiryRepository inquiryRepository;
    private final UserInquiryMapper inquiryMapper;
    private final AuthService authService;

    @Override
    public UserInquiryResponseDTO createInquiry(CreateUserInquiryRequestDTO dto) {
        UserEntity user = authService.getAuthenticatedUser();

        UserInquiryEntity inquiry = inquiryMapper.toEntity(dto);
        inquiry.setUserInquiry(user);
        inquiry.setAnswered(false);
        inquiry.setAnswer(null);
        inquiry.setAnswerDate(null);
        inquiry.setAnsweredBy(null);

        inquiry = inquiryRepository.save(inquiry);
        return inquiryMapper.toDto(inquiry);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserInquiryResponseDTO> listAllInquiries(Boolean answered, Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("inquiryDate").descending());

        Page<UserInquiryEntity> inquiries;

        if (answered != null && userId != null) {
            inquiries = inquiryRepository.findByAnsweredAndUserInquiryId(answered, userId, pageable);
        } else if (answered != null) {
            inquiries = inquiryRepository.findByAnswered(answered, pageable);
        } else if (userId != null) {
            inquiries = inquiryRepository.findByUserInquiryId(userId, pageable);
        } else {
            inquiries = inquiryRepository.findAll(pageable);
        }

        return inquiries.map(inquiryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserInquiryResponseDTO> listUserInquiries() {
        UserEntity user = authService.getAuthenticatedUser();
        List<UserInquiryEntity> inquiries = inquiryRepository.findByUserInquiryIdOrderByInquiryDateDesc(user.getId());
        return inquiries.stream()
                .map(inquiryMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserInquiryResponseDTO getInquiryById(Long inquiryId) {
        UserEntity user = authService.getAuthenticatedUser();
        UserInquiryEntity inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new ResourceNotFoundException("Inquiry not found"));

        boolean isAdmin = user.getRole().equals(Role.ADMIN);
        boolean isOwner = inquiry.getUserInquiry().getId().equals(user.getId());

        if (!isAdmin && !isOwner) {
            throw new UserAccessDeniedException("No permission to access this inquiry");
        }

        return inquiryMapper.toDto(inquiry);
    }

    @Override
    public UserInquiryResponseDTO answerInquiry(AnswerInquiryRequestDTO dto) {
        UserEntity adminUser = authService.getAuthenticatedUser();

        if (!adminUser.getRole().equals(Role.ADMIN)) {
            throw new UserAccessDeniedException("Only admin can answer inquiries");
        }

        UserInquiryEntity inquiry = inquiryRepository.findById(dto.getInquiryId())
                .orElseThrow(() -> new ResourceNotFoundException("Inquiry not found"));

        if (Boolean.TRUE.equals(inquiry.getAnswered())) {
            throw new InquiryAlreadyAnsweredException("Inquiry already answered");
        }

        inquiry.setAnswered(true);
        inquiry.setAnswer(dto.getAnswer());
        inquiry.setAnswerDate(LocalDateTime.now());
        inquiry.setAnsweredBy(adminUser);

        inquiry = inquiryRepository.save(inquiry);
        return inquiryMapper.toDto(inquiry);
    }

    @Override
    public void deleteInquiry(Long inquiryId) {
        UserEntity currentUser = authService.getAuthenticatedUser();

        UserInquiryEntity inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new ResourceNotFoundException("Inquiry not found"));

        boolean isAdmin = currentUser.getRole().equals(Role.ADMIN);
        boolean isOwner = inquiry.getUserInquiry().getId().equals(currentUser.getId());

        if (!isAdmin && !isOwner) {
            throw new UserAccessDeniedException("You can only delete your own inquiries");
        }

        inquiryRepository.deleteById(inquiryId);
    }
}