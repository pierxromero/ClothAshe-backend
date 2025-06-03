package com.clothashe.clotashe_backend.repository.order;

import com.clothashe.clotashe_backend.model.entity.order.OrderEntity;
import com.clothashe.clotashe_backend.model.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Page<OrderEntity> findByUserId(Long userId, Pageable pageable);

    Page<OrderEntity> findByStatus(OrderStatus status, Pageable pageable);

    Page<OrderEntity> findByStatusAndUserId(OrderStatus status, Long userId, Pageable pageable);

}