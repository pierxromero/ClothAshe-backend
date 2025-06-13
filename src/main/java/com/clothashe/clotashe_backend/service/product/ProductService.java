package com.clothashe.clotashe_backend.service.product;

import com.clothashe.clotashe_backend.model.dto.product.create.CreateProductRequestDTO;
import com.clothashe.clotashe_backend.model.dto.product.response.ProductResponseDTO;
import com.clothashe.clotashe_backend.model.dto.product.update.UpdateProductRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    Page<ProductResponseDTO> findAll(Pageable pageable);

    ProductResponseDTO findById(Long id);

    ProductResponseDTO create(CreateProductRequestDTO dto);

    ProductResponseDTO update(Long id, UpdateProductRequestDTO dto);

    void delete(Long id);

    Page<ProductResponseDTO> findByCategoryId(Long categoryId, Pageable pageable);

    Page<ProductResponseDTO> findByPriceRange(Double minPrice, Double maxPrice, Pageable pageable);

    Page<ProductResponseDTO> findByStockAvailability(boolean onlyWithStock, Pageable pageable);

    List<ProductResponseDTO> findTop10ByRating();
}