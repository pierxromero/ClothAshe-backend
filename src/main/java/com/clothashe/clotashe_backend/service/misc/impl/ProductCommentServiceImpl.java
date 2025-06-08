package com.clothashe.clotashe_backend.service.misc.impl;

import com.clothashe.clotashe_backend.mapper.misc.ProductCommentMapper;
import com.clothashe.clotashe_backend.model.dto.review.create.CreateProductCommentRequestDTO;
import com.clothashe.clotashe_backend.model.dto.review.response.ProductCommentResponseDTO;
import com.clothashe.clotashe_backend.model.dto.review.update.UpdateProductCommentRequestDTO;
import com.clothashe.clotashe_backend.model.entity.product.ProductEntity;
import com.clothashe.clotashe_backend.model.entity.review.ProductCommentEntity;
import com.clothashe.clotashe_backend.model.entity.user.UserEntity;
import com.clothashe.clotashe_backend.model.enums.Role;
import com.clothashe.clotashe_backend.repository.auth.UserRepository;
import com.clothashe.clotashe_backend.repository.misc.ProductCommentRepository;
import com.clothashe.clotashe_backend.repository.product.ProductRepository;
import com.clothashe.clotashe_backend.service.auth.AuthService;
import com.clothashe.clotashe_backend.service.misc.ProductCommentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductCommentServiceImpl implements ProductCommentService {

    private final ProductCommentRepository commentRepository;
    private final ProductCommentMapper commentMapper;
    private final AuthService authService;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public ProductCommentResponseDTO createComment(CreateProductCommentRequestDTO dto) {
        ProductEntity product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + dto.getProductId()));

        UserEntity user = authService.getAuthenticatedUser();

        boolean alreadyCommented = commentRepository.existsByUserIdAndProductId(user.getId(), product.getId());
        if (alreadyCommented) {
            throw new IllegalArgumentException("You have already commented on this product.");
        }

        ProductCommentEntity comment = commentMapper.fromCreateDTO(dto);
        comment.setProduct(product);
        comment.setUser(user);

        return commentMapper.toResponseDTO(commentRepository.save(comment));
    }

    @Override
    public ProductCommentResponseDTO updateOwnComment(Long commentId, UpdateProductCommentRequestDTO dto) {
        ProductCommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with ID: " + commentId));

        UserEntity user = authService.getAuthenticatedUser();

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You are not authorized to update this comment.");
        }

        commentMapper.updateEntityFromDTO(dto, comment);
        return commentMapper.toResponseDTO(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(Long commentId) {
        UserEntity user = authService.getAuthenticatedUser();
        ProductCommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with ID: " + commentId));

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You are not authorized to delete this comment.");
        }

        commentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductCommentResponseDTO> getCommentsByUser(Long userId) {
        return commentRepository.findByUserId(userId)
                .stream()
                .map(commentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductCommentResponseDTO> getMyComments() {
        UserEntity currentUser = authService.getAuthenticatedUser();

        return commentRepository.findByUserId(currentUser.getId())
                .stream()
                .map(commentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    @Override
    public List<ProductCommentResponseDTO> getCommentsByProduct(Long productId) {
        return commentRepository.findByProductId(productId)
                .stream()
                .map(commentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductCommentResponseDTO> getAllComments() {
        if (!authService.getAuthenticatedUser().getRole().equals(Role.ADMIN)) {
            throw new AccessDeniedException("Only admins can access this");
        }
        return commentRepository.findAll()
                .stream()
                .map(commentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public ProductCommentResponseDTO getCommentById(Long commentId) {
        if (!authService.getAuthenticatedUser().getRole().equals(Role.ADMIN)) {
            throw new AccessDeniedException("Only admins can access this");
        }
        ProductCommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with ID: " + commentId));
        return commentMapper.toResponseDTO(comment);
    }

}