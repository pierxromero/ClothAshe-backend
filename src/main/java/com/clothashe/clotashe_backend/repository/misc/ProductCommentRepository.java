package com.clothashe.clotashe_backend.repository.misc;

import com.clothashe.clotashe_backend.model.entity.review.ProductCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCommentRepository extends JpaRepository<ProductCommentEntity, Long> {
    List<ProductCommentEntity> findByUserId(Long userId);
    List<ProductCommentEntity> findByProductId(Long productId);
    boolean existsByUserIdAndProductId(Long userId, Long productId);
}