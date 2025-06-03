package com.clothashe.clotashe_backend.service.product;

import com.clothashe.clotashe_backend.model.dto.product.create.CreateSizeRequestDTO;
import com.clothashe.clotashe_backend.model.dto.product.response.SizeResponseDTO;
import com.clothashe.clotashe_backend.model.dto.product.update.UpdateSizeRequestDTO;

import java.util.List;

public interface SizeService {

    SizeResponseDTO create(CreateSizeRequestDTO dto);

    SizeResponseDTO update(Long id, UpdateSizeRequestDTO dto);

    SizeResponseDTO findById(Long id);

    List<SizeResponseDTO> findAll();

    void delete(Long id);
}