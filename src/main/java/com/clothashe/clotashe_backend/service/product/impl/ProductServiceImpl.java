package com.clothashe.clotashe_backend.service.product.impl;
import com.clothashe.clotashe_backend.exception.products.CategoryAlreadyExistsException;
import com.clothashe.clotashe_backend.exception.products.*;
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
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        return productMapper.toDto(product);
    }

    @Override
    @Transactional
    public ProductResponseDTO create(CreateProductRequestDTO dto) {
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
    public List<ProductResponseDTO> findByCategoryId(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new CategoryAlreadyExistsException("Category not found with id: " + categoryId);
        }

        List<ProductEntity> products = productRepository.findByCategoryId(categoryId);

        return products.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findByPriceRange(Double minPrice, Double maxPrice) {
        if (minPrice == null || maxPrice == null) {
            throw new PriceRangeException("Both minPrice and maxPrice must be provided.");
        }

        if (minPrice > maxPrice) {
            throw new PriceRangeException("minPrice cannot be greater than maxPrice.");
        }

        List<ProductEntity> products = productRepository.findByPriceBetween(minPrice, maxPrice);

        return products.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findByStockAvailability(boolean onlyWithStock) {
        List<ProductEntity> products;

        if (onlyWithStock) {
            products = productRepository.findByStockGreaterThan(0);
        } else {
            products = productRepository.findByStockIsNullOrStockLessThanEqual(0);
        }

        return products.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
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