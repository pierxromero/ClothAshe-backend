package com.clothashe.clotashe_backend.service.misc;

import com.clothashe.clotashe_backend.model.dto.user.create.CreateFavoriteProductRequestDTO;
import com.clothashe.clotashe_backend.model.dto.user.response.FavoriteProductResponseDTO;

import java.util.List;

public interface FavoriteProductService {

    FavoriteProductResponseDTO addFavorite(CreateFavoriteProductRequestDTO dto);
    List<FavoriteProductResponseDTO> getMyFavorites();
    List<FavoriteProductResponseDTO> getFavoritesByUserId(Long userId);
    void removeFavorite(Long favoriteId);
    FavoriteProductResponseDTO getFavoriteById(Long favoriteId);
    boolean isProductFavoritedByUser(Long userId, Long productId);
}