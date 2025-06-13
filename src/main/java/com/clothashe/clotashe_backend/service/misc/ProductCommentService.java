package com.clothashe.clotashe_backend.service.misc;

import com.clothashe.clotashe_backend.model.dto.review.create.CreateProductCommentRequestDTO;
import com.clothashe.clotashe_backend.model.dto.review.response.ProductCommentResponseDTO;
import com.clothashe.clotashe_backend.model.dto.review.update.UpdateProductCommentRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductCommentService {
    ProductCommentResponseDTO createComment(CreateProductCommentRequestDTO dto);

    ProductCommentResponseDTO updateOwnComment(Long commentId, UpdateProductCommentRequestDTO dto);

    List<ProductCommentResponseDTO> getMyComments();

    void deleteComment(Long commentId);

    List<ProductCommentResponseDTO> getCommentsByUser(Long userId);

    Page<ProductCommentResponseDTO> getCommentsByProduct(Long productId, Pageable pageable);

    Page<ProductCommentResponseDTO> getAllComments(Pageable pageable);

    ProductCommentResponseDTO getCommentById(Long commentId);
}