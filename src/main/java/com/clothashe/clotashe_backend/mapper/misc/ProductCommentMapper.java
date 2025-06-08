package com.clothashe.clotashe_backend.mapper.misc;

import com.clothashe.clotashe_backend.model.dto.review.create.CreateProductCommentRequestDTO;
import com.clothashe.clotashe_backend.model.dto.review.response.ProductCommentResponseDTO;
import com.clothashe.clotashe_backend.model.dto.review.update.UpdateProductCommentRequestDTO;
import com.clothashe.clotashe_backend.model.entity.review.ProductCommentEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductCommentMapper {
    @Mapping(source = "user.id", target = "userId")
    ProductCommentResponseDTO toResponseDTO(ProductCommentEntity entity);


    ProductCommentEntity fromCreateDTO(CreateProductCommentRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(UpdateProductCommentRequestDTO dto, @MappingTarget ProductCommentEntity entity);
}