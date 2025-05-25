package com.clothashe.clotashe_backend.service.product;

import com.clothashe.clotashe_backend.model.dto.product.SizeDTO;

import java.util.List;

public interface SizeService {
    SizeDTO create(SizeDTO dto);

    SizeDTO update(Long id, SizeDTO dto);

    SizeDTO getById(Long id);

    List<SizeDTO> getAll();

    void delete(Long id);
}