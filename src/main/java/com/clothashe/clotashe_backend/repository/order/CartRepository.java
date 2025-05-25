package com.clothashe.clotashe_backend.repository.order;

import com.clothashe.clotashe_backend.model.entity.cart.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {
}