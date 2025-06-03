package com.clothashe.clotashe_backend.repository.order;

import com.clothashe.clotashe_backend.model.entity.order.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {

//    List<OrderItemEntity> findByOrderId(Long orderId);
}