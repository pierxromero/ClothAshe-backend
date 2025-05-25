package com.clothashe.clotashe_backend.service.misc;

import com.clothashe.clotashe_backend.model.dto.review.ProductCommentDTO;

import java.util.List;

public interface ProductCommentService {
    ProductCommentDTO create(ProductCommentDTO dto);

    ProductCommentDTO update(Long id, ProductCommentDTO dto);

    ProductCommentDTO getById(Long id);

    List<ProductCommentDTO> getAll();

    void delete(Long id);
}