package com.clothashe.clotashe_backend.repository.order;

import com.clothashe.clotashe_backend.model.entity.order.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
}