package com.clothashe.clotashe_backend.mapper.misc;

import com.clothashe.clotashe_backend.model.dto.review.ProductCommentDTO;
import com.clothashe.clotashe_backend.model.entity.review.ProductCommentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductCommentMapper {

    ProductCommentDTO toDto(ProductCommentEntity entity);

    ProductCommentEntity toEntity(ProductCommentDTO dto);
}