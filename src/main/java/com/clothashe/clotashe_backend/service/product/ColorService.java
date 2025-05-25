package com.clothashe.clotashe_backend.service.product;

import com.clothashe.clotashe_backend.model.dto.product.ColorDTO;

import java.util.List;

public interface ColorService {
    ColorDTO create(ColorDTO dto);

    ColorDTO update(Long id, ColorDTO dto);

    ColorDTO getById(Long id);

    List<ColorDTO> getAll();

    void delete(Long id);
}