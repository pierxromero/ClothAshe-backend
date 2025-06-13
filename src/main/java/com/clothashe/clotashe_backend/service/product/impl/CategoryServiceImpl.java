package com.clothashe.clotashe_backend.service.product.impl;

import com.clothashe.clotashe_backend.exception.products.CategoryAlreadyExistsException;
import com.clothashe.clotashe_backend.exception.products.CategoryNotFoundException;
import com.clothashe.clotashe_backend.mapper.product.CategoryMapper;
import com.clothashe.clotashe_backend.model.dto.product.create.CreateCategoryRequestDTO;
import com.clothashe.clotashe_backend.model.dto.product.response.CategoryResponseDTO;
import com.clothashe.clotashe_backend.model.dto.product.update.UpdateCategoryRequestDTO;
import com.clothashe.clotashe_backend.model.entity.product.CategoryEntity;
import com.clothashe.clotashe_backend.repository.product.CategoryRepository;
import com.clothashe.clotashe_backend.service.product.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public CategoryResponseDTO create(CreateCategoryRequestDTO dto) {
        if (categoryRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new CategoryAlreadyExistsException("A category with the name '" + dto.getName() + "' already exists.");
        }

        CategoryEntity entity = categoryMapper.toEntity(dto);
        entity.setCreatedAt(LocalDateTime.now());

        CategoryEntity saved = categoryRepository.save(entity);
        return categoryMapper.toDto(saved);
    }

    @Override
    @Transactional
    public CategoryResponseDTO update(Long id, UpdateCategoryRequestDTO dto) {
        CategoryEntity existing = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));

        categoryMapper.updateEntityFromDto(dto, existing);
        CategoryEntity updated = categoryRepository.save(existing);

        return categoryMapper.toDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponseDTO findById(Long id) {
        CategoryEntity entity = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));
        return categoryMapper.toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }
}