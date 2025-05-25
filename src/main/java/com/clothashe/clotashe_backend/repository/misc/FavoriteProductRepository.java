package com.clothashe.clotashe_backend.repository.misc;

import com.clothashe.clotashe_backend.model.entity.user.FavoriteProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteProductRepository extends JpaRepository<FavoriteProductEntity, Long> {
}