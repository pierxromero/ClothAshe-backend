package com.clothashe.clotashe_backend.service.product;

import com.clothashe.clotashe_backend.model.dto.product.create.CreateColorRequestDTO;
import com.clothashe.clotashe_backend.model.dto.product.response.ColorResponseDTO;
import com.clothashe.clotashe_backend.model.dto.product.update.UpdateColorRequestDTO;

import java.util.List;

public interface ColorService {
    ColorResponseDTO create(CreateColorRequestDTO dto);
    ColorResponseDTO update(Long id, UpdateColorRequestDTO dto);
    ColorResponseDTO findById(Long id);
    List<ColorResponseDTO> findAll();
    void delete(Long id);
}