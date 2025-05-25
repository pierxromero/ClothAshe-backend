package com.clothashe.clotashe_backend.service.misc;

import com.clothashe.clotashe_backend.model.dto.user.FavoriteProductDTO;

import java.util.List;

public interface FavoriteProductService {
    FavoriteProductDTO create(FavoriteProductDTO dto);

    FavoriteProductDTO update(Long id, FavoriteProductDTO dto);

    FavoriteProductDTO getById(Long id);

    List<FavoriteProductDTO> getAll();

    void delete(Long id);
}