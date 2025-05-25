package com.clothashe.clotashe_backend.repository.product;

import com.clothashe.clotashe_backend.model.entity.product.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}