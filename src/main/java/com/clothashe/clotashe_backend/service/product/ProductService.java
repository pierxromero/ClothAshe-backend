package com.clothashe.clotashe_backend.service.product;

import com.clothashe.clotashe_backend.model.dto.product.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO create(ProductDTO dto);

    ProductDTO update(Long id, ProductDTO dto);

    ProductDTO getById(Long id);

    List<ProductDTO> getAll();

    void delete(Long id);
}