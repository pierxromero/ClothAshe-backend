package com.clothashe.clotashe_backend.service.product.impl;

import com.clothashe.clotashe_backend.exception.products.SizeAlreadyExistsException;
import com.clothashe.clotashe_backend.exception.products.SizeNotFoundException;
import com.clothashe.clotashe_backend.mapper.product.SizeMapper;
import com.clothashe.clotashe_backend.model.dto.product.create.CreateSizeRequestDTO;
import com.clothashe.clotashe_backend.model.dto.product.response.SizeResponseDTO;
import com.clothashe.clotashe_backend.model.dto.product.update.UpdateSizeRequestDTO;
import com.clothashe.clotashe_backend.model.entity.product.SizeEntity;
import com.clothashe.clotashe_backend.repository.product.SizeRepository;
import com.clothashe.clotashe_backend.service.product.SizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class SizeServiceImpl implements SizeService {

    private final SizeRepository sizeRepository;
    private final SizeMapper sizeMapper;

    @Override
    @Transactional
    public SizeResponseDTO create(CreateSizeRequestDTO dto) {
        if (sizeRepository.existsByCodeIgnoreCase(dto.getCode())) {
            throw new SizeAlreadyExistsException("A size with the code '" + dto.getCode() + "' already exists.");
        }
        SizeEntity entity = sizeMapper.toEntity(dto);
        SizeEntity saved = sizeRepository.save(entity);
        return sizeMapper.toDto(saved);
    }

    @Override
    @Transactional
    public SizeResponseDTO update(Long id, UpdateSizeRequestDTO dto) {
        SizeEntity existing = sizeRepository.findById(id)
                .orElseThrow(() -> new SizeNotFoundException("Size not found with id: " + id));

        sizeMapper.updateEntityFromDto(dto, existing);
        SizeEntity updated = sizeRepository.save(existing);
        return sizeMapper.toDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public SizeResponseDTO findById(Long id) {
        SizeEntity entity = sizeRepository.findById(id)
                .orElseThrow(() -> new SizeNotFoundException("Size not found with id: " + id));
        return sizeMapper.toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SizeResponseDTO> findAll() {
        return sizeRepository.findAll()
                .stream()
                .map(sizeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!sizeRepository.existsById(id)) {
            throw new SizeAlreadyExistsException("Size not found with id: " + id);
        }
        sizeRepository.deleteById(id);
    }
}