package com.clothashe.clotashe_backend.service.misc.impl;

import com.clothashe.clotashe_backend.mapper.misc.FavoriteProductMapper;
import com.clothashe.clotashe_backend.model.dto.user.create.CreateFavoriteProductRequestDTO;
import com.clothashe.clotashe_backend.model.dto.user.response.FavoriteProductResponseDTO;
import com.clothashe.clotashe_backend.model.entity.product.ProductEntity;
import com.clothashe.clotashe_backend.model.entity.user.FavoriteProductEntity;
import com.clothashe.clotashe_backend.model.entity.user.UserEntity;
import com.clothashe.clotashe_backend.model.enums.Role;
import com.clothashe.clotashe_backend.repository.misc.FavoriteProductRepository;
import com.clothashe.clotashe_backend.repository.product.ProductRepository;
import com.clothashe.clotashe_backend.service.auth.AuthService;
import com.clothashe.clotashe_backend.service.misc.FavoriteProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class FavoriteProductServiceImpl implements FavoriteProductService {

    private final FavoriteProductRepository favoriteProductRepository;
    private final ProductRepository productRepository;
    private final AuthService authService;
    private final FavoriteProductMapper favoriteProductMapper;

    @Override
    @Transactional
    public FavoriteProductResponseDTO addFavorite(CreateFavoriteProductRequestDTO dto) {
        UserEntity user = authService.getAuthenticatedUser();

        ProductEntity product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + dto.getProductId()));

        if (isProductFavoritedByUser(user.getId(), product.getId())) {
            throw new IllegalArgumentException("Product is already in favorites");
        }

        FavoriteProductEntity favorite = FavoriteProductEntity.builder()
                .user(user)
                .productFavorite(product)
                .addedDate(LocalDateTime.now())
                .build();

        FavoriteProductEntity saved = favoriteProductRepository.save(favorite);

        return favoriteProductMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FavoriteProductResponseDTO> getMyFavorites() {
        UserEntity user = authService.getAuthenticatedUser();

        List<FavoriteProductEntity> favorites = favoriteProductRepository.findByUserId(user.getId());

        return favorites.stream()
                .map(favoriteProductMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FavoriteProductResponseDTO> getFavoritesByUserId(Long userId) {
        UserEntity requester = authService.getAuthenticatedUser();

        if (!requester.getRole().equals(Role.ADMIN)) {
            throw new AccessDeniedException("Only admins can access other users' favorites");
        }
        List<FavoriteProductEntity> favorites = favoriteProductRepository.findByUserId(userId);

        return favorites.stream()
                .map(favoriteProductMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void removeFavorite(Long favoriteId) {
        FavoriteProductEntity favorite = favoriteProductRepository.findById(favoriteId)
                .orElseThrow(() -> new EntityNotFoundException("Favorite product not found with ID: " + favoriteId));

        UserEntity user = authService.getAuthenticatedUser();

        if (!favorite.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You are not authorized to delete this favorite");
        }

        favoriteProductRepository.delete(favorite);
    }

    @Override
    @Transactional(readOnly = true)
    public FavoriteProductResponseDTO getFavoriteById(Long favoriteId) {
        FavoriteProductEntity favorite = favoriteProductRepository.findById(favoriteId)
                .orElseThrow(() -> new EntityNotFoundException("Favorite product not found with ID: " + favoriteId));

        UserEntity user = authService.getAuthenticatedUser();

        if (!favorite.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You are not authorized to view this favorite");
        }

        return favoriteProductMapper.toDto(favorite);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isProductFavoritedByUser(Long userId, Long productId) {
        return favoriteProductRepository.existsByUserIdAndProductFavoriteId(userId, productId);
    }
}
