package com.clothashe.clotashe_backend.repository.order;

import com.clothashe.clotashe_backend.model.entity.order.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {

}