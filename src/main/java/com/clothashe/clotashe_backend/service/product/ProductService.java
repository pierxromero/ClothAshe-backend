package com.clothashe.clotashe_backend.service.product;

import com.clothashe.clotashe_backend.model.dto.product.create.CreateProductRequestDTO;
import com.clothashe.clotashe_backend.model.dto.product.response.ProductResponseDTO;
import com.clothashe.clotashe_backend.model.dto.product.update.UpdateProductRequestDTO;

import java.util.List;

public interface ProductService {

    List<ProductResponseDTO> findAll();

    ProductResponseDTO findById(Long id);

    ProductResponseDTO create(CreateProductRequestDTO dto);

    ProductResponseDTO update(Long id, UpdateProductRequestDTO dto);

    void delete(Long id);

    List<ProductResponseDTO> findByCategoryId(Long categoryId);

    List<ProductResponseDTO> findByPriceRange(Double minPrice, Double maxPrice);

    List<ProductResponseDTO> findByStockAvailability(boolean onlyWithStock);

    List<ProductResponseDTO> findTop10ByRating();
}