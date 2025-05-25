package com.clothashe.clotashe_backend.service.misc.impl;

import com.clothashe.clotashe_backend.exception.ResourceNotFoundException;
import com.clothashe.clotashe_backend.mapper.misc.ProductCommentMapper;
import com.clothashe.clotashe_backend.model.dto.review.ProductCommentDTO;
import com.clothashe.clotashe_backend.model.entity.review.ProductCommentEntity;
import com.clothashe.clotashe_backend.repository.misc.ProductCommentRepository;
import com.clothashe.clotashe_backend.service.misc.ProductCommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCommentServiceImpl implements ProductCommentService {

    private final ProductCommentRepository productCommentRepository;
    private final ProductCommentMapper productCommentMapper;

    public ProductCommentServiceImpl(ProductCommentRepository productCommentRepository, ProductCommentMapper productCommentMapper) {
        this.productCommentRepository = productCommentRepository;
        this.productCommentMapper = productCommentMapper;
    }

    @Override
    public ProductCommentDTO create(ProductCommentDTO dto) {
        ProductCommentEntity entity = productCommentMapper.toEntity(dto);
        return productCommentMapper.toDto(productCommentRepository.save(entity));
    }

    @Override
    public ProductCommentDTO update(Long id, ProductCommentDTO dto) {
        ProductCommentEntity existing = productCommentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductComment not found with id: " + id));
        ProductCommentEntity updated = productCommentMapper.toEntity(dto);
        updated.setId(id);
        return productCommentMapper.toDto(productCommentRepository.save(updated));
    }

    @Override
    public ProductCommentDTO getById(Long id) {
        return productCommentRepository.findById(id)
                .map(productCommentMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("ProductComment not found with id: " + id));
    }

    @Override
    public List<ProductCommentDTO> getAll() {
        return productCommentRepository.findAll()
                .stream()
                .map(productCommentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!productCommentRepository.existsById(id)) {
            throw new ResourceNotFoundException("ProductComment not found with id: " + id);
        }
        productCommentRepository.deleteById(id);
    }
}