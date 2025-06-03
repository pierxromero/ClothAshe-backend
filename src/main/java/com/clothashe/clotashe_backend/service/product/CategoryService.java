package com.clothashe.clotashe_backend.service.product;

import com.clothashe.clotashe_backend.model.dto.product.create.CreateCategoryRequestDTO;
import com.clothashe.clotashe_backend.model.dto.product.response.CategoryResponseDTO;
import com.clothashe.clotashe_backend.model.dto.product.update.UpdateCategoryRequestDTO;

import java.util.List;

public interface CategoryService {
    CategoryResponseDTO create(CreateCategoryRequestDTO dto);
    CategoryResponseDTO update(Long id, UpdateCategoryRequestDTO dto);
    CategoryResponseDTO findById(Long id);
    List<CategoryResponseDTO> findAll();
    void delete(Long id);
}