package com.clothashe.clotashe_backend.service.product.impl;

import com.clothashe.clotashe_backend.exception.ResourceNotFoundException;
import com.clothashe.clotashe_backend.mapper.product.ProductMapper;
import com.clothashe.clotashe_backend.model.dto.product.ProductDTO;
import com.clothashe.clotashe_backend.model.entity.product.ProductEntity;
import com.clothashe.clotashe_backend.repository.product.ProductRepository;
import com.clothashe.clotashe_backend.service.product.ProductService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDTO create(ProductDTO dto) {
        ProductEntity entity = productMapper.toEntity(dto);
        return productMapper.toDto(productRepository.save(entity));
    }

    @Override
    public ProductDTO update(Long id, ProductDTO dto) {
        ProductEntity existing = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        ProductEntity updated = productMapper.toEntity(dto);
        updated.setId(id);
        return productMapper.toDto(productRepository.save(updated));
    }

    @Override
    public ProductDTO getById(Long id) {
        return productRepository.findById(id)
            .map(productMapper::toDto)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    @Override
    public List<ProductDTO> getAll() {
        return productRepository.findAll()
            .stream()
            .map(productMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}