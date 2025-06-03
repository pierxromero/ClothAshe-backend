package com.clothashe.clotashe_backend.service.product.impl;
import org.springframework.transaction.annotation.Transactional;
import com.clothashe.clotashe_backend.mapper.product.ProductMapper;
import com.clothashe.clotashe_backend.model.dto.product.create.CreateProductRequestDTO;
import com.clothashe.clotashe_backend.model.dto.product.response.ProductResponseDTO;
import com.clothashe.clotashe_backend.model.dto.product.update.UpdateProductRequestDTO;
import com.clothashe.clotashe_backend.model.entity.product.ProductEntity;
import com.clothashe.clotashe_backend.repository.product.*;
import com.clothashe.clotashe_backend.service.product.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    private final CategoryRepository categoryRepository;
    private final SizeRepository sizeRepository;
    private final ColorRepository colorRepository;
    private final BrandRepository brandRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDTO findById(Long id) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        return productMapper.toDto(product);
    }

    @Override
    @Transactional
    public ProductResponseDTO create(CreateProductRequestDTO dto) {
        ProductEntity entity = productMapper.toEntity(dto);

        entity.setCategory(categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + dto.getCategoryId())));
        entity.setSize(sizeRepository.findById(dto.getSizeId())
                .orElseThrow(() -> new EntityNotFoundException("Size not found with id: " + dto.getSizeId())));
        entity.setColor(colorRepository.findById(dto.getColorId())
                .orElseThrow(() -> new EntityNotFoundException("Color not found with id: " + dto.getColorId())));
        entity.setBrand(brandRepository.findById(dto.getBrandId())
                .orElseThrow(() -> new EntityNotFoundException("Brand not found with id: " + dto.getBrandId())));

        entity.setCreatedAt(LocalDateTime.now());

        return productMapper.toDto(productRepository.save(entity));
    }

    @Override
    @Transactional
    public ProductResponseDTO update(Long id, UpdateProductRequestDTO dto) {
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));

        productMapper.updateEntityFromDto(dto, entity);

        if (dto.getCategoryId() != null) {
            entity.setCategory(categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + dto.getCategoryId())));
        }
        if (dto.getSizeId() != null) {
            entity.setSize(sizeRepository.findById(dto.getSizeId())
                    .orElseThrow(() -> new EntityNotFoundException("Size not found with id: " + dto.getSizeId())));
        }
        if (dto.getColorId() != null) {
            entity.setColor(colorRepository.findById(dto.getColorId())
                    .orElseThrow(() -> new EntityNotFoundException("Color not found with id: " + dto.getColorId())));
        }
        if (dto.getBrandId() != null) {
            entity.setBrand(brandRepository.findById(dto.getBrandId())
                    .orElseThrow(() -> new EntityNotFoundException("Brand not found with id: " + dto.getBrandId())));
        }

        return productMapper.toDto(productRepository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}