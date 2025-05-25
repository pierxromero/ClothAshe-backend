package com.clothashe.clotashe_backend.service.product;

import com.clothashe.clotashe_backend.model.dto.product.BrandDTO;

import java.util.List;

public interface BrandService {
    BrandDTO create(BrandDTO dto);

    BrandDTO update(Long id, BrandDTO dto);

    BrandDTO getById(Long id);

    List<BrandDTO> getAll();

    void delete(Long id);
}