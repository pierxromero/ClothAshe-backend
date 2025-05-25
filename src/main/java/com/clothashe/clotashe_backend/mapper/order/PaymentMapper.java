package com.clothashe.clotashe_backend.mapper.order;

import com.clothashe.clotashe_backend.model.dto.order.PaymentDTO;
import com.clothashe.clotashe_backend.model.entity.order.PaymentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentDTO toDto(PaymentEntity entity);

    PaymentEntity toEntity(PaymentDTO dto);
}