package com.clothashe.clotashe_backend.service.product;

import com.clothashe.clotashe_backend.model.dto.product.create.CreateBrandRequestDTO;
import com.clothashe.clotashe_backend.model.dto.product.response.BrandResponseDTO;
import com.clothashe.clotashe_backend.model.dto.product.update.UpdateBrandRequestDTO;

import java.util.List;

public interface BrandService {

    BrandResponseDTO create(CreateBrandRequestDTO dto);

    BrandResponseDTO update(Long id, UpdateBrandRequestDTO dto);

    BrandResponseDTO findById(Long id);

    List<BrandResponseDTO> findAll();

    void delete(Long id);
}