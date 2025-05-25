package com.clothashe.clotashe_backend.repository.misc;

import com.clothashe.clotashe_backend.model.entity.review.ProductCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCommentRepository extends JpaRepository<ProductCommentEntity, Long> {
}