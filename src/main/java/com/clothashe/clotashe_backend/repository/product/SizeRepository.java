package com.clothashe.clotashe_backend.repository.product;

import com.clothashe.clotashe_backend.model.entity.product.SizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends JpaRepository<SizeEntity, Long> {
}