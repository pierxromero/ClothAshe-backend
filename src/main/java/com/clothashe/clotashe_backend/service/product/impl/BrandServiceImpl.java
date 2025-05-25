package com.clothashe.clotashe_backend.service.product.impl;

import com.clothashe.clotashe_backend.exception.ResourceNotFoundException;
import com.clothashe.clotashe_backend.mapper.product.BrandMapper;
import com.clothashe.clotashe_backend.model.dto.product.BrandDTO;
import com.clothashe.clotashe_backend.model.entity.product.BrandEntity;
import com.clothashe.clotashe_backend.repository.product.BrandRepository;
import com.clothashe.clotashe_backend.service.product.BrandService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    public BrandServiceImpl(BrandRepository brandRepository, BrandMapper brandMapper) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }

    @Override
    public BrandDTO create(BrandDTO dto) {
        BrandEntity entity = brandMapper.toEntity(dto);
        return brandMapper.toDto(brandRepository.save(entity));
    }

    @Override
    public BrandDTO update(Long id, BrandDTO dto) {
        BrandEntity existing = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + id));
        BrandEntity updated = brandMapper.toEntity(dto);
        updated.setId(id);
        return brandMapper.toDto(brandRepository.save(updated));
    }

    @Override
    public BrandDTO getById(Long id) {
        return brandRepository.findById(id)
                .map(brandMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + id));
    }

    @Override
    public List<BrandDTO> getAll() {
        return brandRepository.findAll()
                .stream()
                .map(brandMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!brandRepository.existsById(id)) {
            throw new ResourceNotFoundException("Brand not found with id: " + id);
        }
        brandRepository.deleteById(id);
    }
}