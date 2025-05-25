package com.clothashe.clotashe_backend.service.misc.impl;

import com.clothashe.clotashe_backend.exception.ResourceNotFoundException;
import com.clothashe.clotashe_backend.mapper.misc.UserInquiryMapper;
import com.clothashe.clotashe_backend.model.dto.user.UserInquiryDTO;
import com.clothashe.clotashe_backend.model.entity.user.UserInquiryEntity;
import com.clothashe.clotashe_backend.repository.misc.UserInquiryRepository;
import com.clothashe.clotashe_backend.service.misc.UserInquiryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserInquiryServiceImpl implements UserInquiryService {

    private final UserInquiryRepository userInquiryRepository;
    private final UserInquiryMapper userInquiryMapper;

    public UserInquiryServiceImpl(UserInquiryRepository userInquiryRepository, UserInquiryMapper userInquiryMapper) {
        this.userInquiryRepository = userInquiryRepository;
        this.userInquiryMapper = userInquiryMapper;
    }

    @Override
    public UserInquiryDTO create(UserInquiryDTO dto) {
        UserInquiryEntity entity = userInquiryMapper.toEntity(dto);
        return userInquiryMapper.toDto(userInquiryRepository.save(entity));
    }

    @Override
    public UserInquiryDTO update(Long id, UserInquiryDTO dto) {
        UserInquiryEntity existing = userInquiryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserInquiry not found with id: " + id));
        UserInquiryEntity updated = userInquiryMapper.toEntity(dto);
        updated.setId(id);
        return userInquiryMapper.toDto(userInquiryRepository.save(updated));
    }

    @Override
    public UserInquiryDTO getById(Long id) {
        return userInquiryRepository.findById(id)
                .map(userInquiryMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("UserInquiry not found with id: " + id));
    }

    @Override
    public List<UserInquiryDTO> getAll() {
        return userInquiryRepository.findAll()
                .stream()
                .map(userInquiryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!userInquiryRepository.existsById(id)) {
            throw new ResourceNotFoundException("UserInquiry not found with id: " + id);
        }
        userInquiryRepository.deleteById(id);
    }
}