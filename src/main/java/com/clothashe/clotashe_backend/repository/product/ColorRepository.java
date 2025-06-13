package com.clothashe.clotashe_backend.repository.product;

import com.clothashe.clotashe_backend.model.entity.product.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<ColorEntity, Long> {
    boolean existsByNameIgnoreCase(String name);
}