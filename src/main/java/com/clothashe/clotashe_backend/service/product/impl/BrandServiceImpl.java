package com.clothashe.clotashe_backend.service.product.impl;

import com.clothashe.clotashe_backend.exception.ResourceAlreadyExistsException;
import com.clothashe.clotashe_backend.exception.ResourceNotFoundException;
import com.clothashe.clotashe_backend.mapper.product.BrandMapper;
import com.clothashe.clotashe_backend.model.dto.product.create.CreateBrandRequestDTO;
import com.clothashe.clotashe_backend.model.dto.product.response.BrandResponseDTO;
import com.clothashe.clotashe_backend.model.dto.product.update.UpdateBrandRequestDTO;
import com.clothashe.clotashe_backend.model.entity.product.BrandEntity;
import com.clothashe.clotashe_backend.repository.product.BrandRepository;
import com.clothashe.clotashe_backend.service.product.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Override
    @Transactional
    public BrandResponseDTO create(CreateBrandRequestDTO dto) {
        if (brandRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new ResourceAlreadyExistsException("Ya existe una marca con el nombre: " + dto.getName());
        }
        BrandEntity entity = brandMapper.toEntity(dto);
        BrandEntity saved = brandRepository.save(entity);
        return brandMapper.toDto(saved);
    }

    @Override
    @Transactional
    public BrandResponseDTO update(Long id, UpdateBrandRequestDTO dto) {
        BrandEntity existing = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + id));

        brandMapper.updateEntityFromDto(dto, existing);
        BrandEntity updated = brandRepository.save(existing);

        return brandMapper.toDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public BrandResponseDTO findById(Long id) {
        BrandEntity entity = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + id));
        return brandMapper.toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BrandResponseDTO> findAll() {
        return brandRepository.findAll()
                .stream()
                .map(brandMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!brandRepository.existsById(id)) {
            throw new ResourceNotFoundException("Brand not found with id: " + id);
        }
        brandRepository.deleteById(id);
    }
}