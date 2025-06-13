package com.clothashe.clotashe_backend.service.product.impl;
import com.clothashe.clotashe_backend.exception.products.CategoryAlreadyExistsException;
import com.clothashe.clotashe_backend.exception.products.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import com.clothashe.clotashe_backend.mapper.product.ProductMapper;
import com.clothashe.clotashe_backend.model.dto.product.create.CreateProductRequestDTO;
import com.clothashe.clotashe_backend.model.dto.product.response.ProductResponseDTO;
import com.clothashe.clotashe_backend.model.dto.product.update.UpdateProductRequestDTO;
import com.clothashe.clotashe_backend.model.entity.product.ProductEntity;
import com.clothashe.clotashe_backend.repository.product.*;
import com.clothashe.clotashe_backend.service.product.ProductService;
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
    public ProductResponseDTO findById(Long id) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        return productMapper.toDto(product);
    }

    @Override
    @Transactional
    public ProductResponseDTO create(CreateProductRequestDTO dto) {
        boolean exists = productRepository.existsByNameAndCategoryIdAndSizeIdAndColorIdAndBrandId(
                dto.getName(),
                dto.getCategoryId(),
                dto.getSizeId(),
                dto.getColorId(),
                dto.getBrandId()
        );
        if (exists) {
            throw new DuplicateProductException("Product already exists with the same name, category, size, color, and brand.");
        }

        ProductEntity entity = productMapper.toEntity(dto);

        entity.setCategory(categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + dto.getCategoryId())));
        entity.setSize(sizeRepository.findById(dto.getSizeId())
                .orElseThrow(() -> new SizeNotFoundException("Size not found with id: " + dto.getSizeId())));
        entity.setColor(colorRepository.findById(dto.getColorId())
                .orElseThrow(() -> new ColorNotFoundException("Color not found with id: " + dto.getColorId())));
        entity.setBrand(brandRepository.findById(dto.getBrandId())
                .orElseThrow(() -> new BrandNotFoundException("Brand not found with id: " + dto.getBrandId())));

        entity.setCreatedAt(LocalDateTime.now());

        return productMapper.toDto(productRepository.save(entity));
    }

    @Override
    @Transactional
    public ProductResponseDTO update(Long id, UpdateProductRequestDTO dto) {
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        productMapper.updateEntityFromDto(dto, entity);

        if (dto.getCategoryId() != null) {
            entity.setCategory(categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + dto.getCategoryId())));
        }
        if (dto.getSizeId() != null) {
            entity.setSize(sizeRepository.findById(dto.getSizeId())
                    .orElseThrow(() -> new SizeNotFoundException("Size not found with id: " + dto.getSizeId())));
        }
        if (dto.getColorId() != null) {
            entity.setColor(colorRepository.findById(dto.getColorId())
                    .orElseThrow(() -> new ColorNotFoundException("Color not found with id: " + dto.getColorId())));
        }
        if (dto.getBrandId() != null) {
            entity.setBrand(brandRepository.findById(dto.getBrandId())
                    .orElseThrow(() -> new BrandNotFoundException("Brand not found with id: " + dto.getBrandId())));
        }

        return productMapper.toDto(productRepository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductAlreadyExistsException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> findAll(Pageable pageable) {
        Page<ProductEntity> page = productRepository.findAll(pageable);
        return page.map(productMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> findByCategoryId(Long categoryId, Pageable pageable) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new CategoryNotFoundException("Category not found with id: " + categoryId);
        }
        Page<ProductEntity> page = productRepository.findByCategoryId(categoryId, pageable);
        return page.map(productMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> findByPriceRange(Double minPrice, Double maxPrice, Pageable pageable) {
        if (minPrice == null || maxPrice == null) {
            throw new PriceRangeException("Both minPrice and maxPrice must be provided.");
        }
        if (minPrice > maxPrice) {
            throw new PriceRangeException("minPrice cannot be greater than maxPrice.");
        }
        Page<ProductEntity> page = productRepository.findByPriceBetween(minPrice, maxPrice, pageable);
        return page.map(productMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> findByStockAvailability(boolean onlyWithStock, Pageable pageable) {
        Page<ProductEntity> page;
        if (onlyWithStock) {
            page = productRepository.findByStockGreaterThan(0, pageable);
        } else {
            page = productRepository.findByStockIsNullOrStockLessThanEqual(0, pageable);
        }
        return page.map(productMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findTop10ByRating() {
        Pageable topTen = PageRequest.of(0, 10); // Página 0, 10 elementos
        return productRepository.findTopRatedProducts(topTen)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}