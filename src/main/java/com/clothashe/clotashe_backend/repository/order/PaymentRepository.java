package com.clothashe.clotashe_backend.repository.order;

import com.clothashe.clotashe_backend.model.entity.order.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
//    Optional<PaymentEntity> findByOrderId(Long orderId);
}