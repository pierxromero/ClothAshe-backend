package com.clothashe.clotashe_backend.repository.misc;

import com.clothashe.clotashe_backend.model.entity.user.FavoriteProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteProductRepository extends JpaRepository<FavoriteProductEntity, Long> {
    List<FavoriteProductEntity> findByUserId(Long userId);

    boolean existsByUserIdAndProductFavoriteId(Long userId, Long productId);
}