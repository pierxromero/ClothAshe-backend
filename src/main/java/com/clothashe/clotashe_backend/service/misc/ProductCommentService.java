package com.clothashe.clotashe_backend.service.misc;

import com.clothashe.clotashe_backend.model.dto.review.create.CreateProductCommentRequestDTO;
import com.clothashe.clotashe_backend.model.dto.review.response.ProductCommentResponseDTO;
import com.clothashe.clotashe_backend.model.dto.review.update.UpdateProductCommentRequestDTO;

import java.util.List;

public interface ProductCommentService {
    ProductCommentResponseDTO createComment(CreateProductCommentRequestDTO dto);

    ProductCommentResponseDTO updateOwnComment(Long commentId, UpdateProductCommentRequestDTO dto);

    List<ProductCommentResponseDTO> getMyComments();

    void deleteComment(Long commentId);

    List<ProductCommentResponseDTO> getCommentsByUser(Long userId);

    List<ProductCommentResponseDTO> getCommentsByProduct(Long productId);

    List<ProductCommentResponseDTO> getAllComments();

    ProductCommentResponseDTO getCommentById(Long commentId);
}