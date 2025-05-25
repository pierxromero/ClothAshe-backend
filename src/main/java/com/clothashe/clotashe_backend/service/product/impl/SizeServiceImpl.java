package com.clothashe.clotashe_backend.service.product.impl;

import com.clothashe.clotashe_backend.exception.ResourceNotFoundException;
import com.clothashe.clotashe_backend.mapper.product.SizeMapper;
import com.clothashe.clotashe_backend.model.dto.product.SizeDTO;
import com.clothashe.clotashe_backend.model.entity.product.SizeEntity;
import com.clothashe.clotashe_backend.repository.product.SizeRepository;
import com.clothashe.clotashe_backend.service.product.SizeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SizeServiceImpl implements SizeService {

    private final SizeRepository sizeRepository;
    private final SizeMapper sizeMapper;

    public SizeServiceImpl(SizeRepository sizeRepository, SizeMapper sizeMapper) {
        this.sizeRepository = sizeRepository;
        this.sizeMapper = sizeMapper;
    }

    @Override
    public SizeDTO create(SizeDTO dto) {
        SizeEntity entity = sizeMapper.toEntity(dto);
        return sizeMapper.toDto(sizeRepository.save(entity));
    }

    @Override
    public SizeDTO update(Long id, SizeDTO dto) {
        SizeEntity existing = sizeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Size not found with id: " + id));
        SizeEntity updated = sizeMapper.toEntity(dto);
        updated.setId(id);
        return sizeMapper.toDto(sizeRepository.save(updated));
    }

    @Override
    public SizeDTO getById(Long id) {
        return sizeRepository.findById(id)
                .map(sizeMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Size not found with id: " + id));
    }

    @Override
    public List<SizeDTO> getAll() {
        return sizeRepository.findAll()
                .stream()
                .map(sizeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!sizeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Size not found with id: " + id);
        }
        sizeRepository.deleteById(id);
    }
}