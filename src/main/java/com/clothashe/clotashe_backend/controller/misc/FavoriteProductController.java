package com.clothashe.clotashe_backend.controller.misc;



import com.clothashe.clotashe_backend.model.dto.user.create.CreateFavoriteProductRequestDTO;
import com.clothashe.clotashe_backend.model.dto.user.response.FavoriteProductResponseDTO;
import com.clothashe.clotashe_backend.service.misc.FavoriteProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
@Validated
public class FavoriteProductController {

    private final FavoriteProductService favoriteProductService;


    @PostMapping
    public ResponseEntity<FavoriteProductResponseDTO> addFavorite(
            @Valid @RequestBody CreateFavoriteProductRequestDTO dto) {
        FavoriteProductResponseDTO created = favoriteProductService.addFavorite(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @GetMapping("/me")
    public ResponseEntity<List<FavoriteProductResponseDTO>> getMyFavorites() {
        List<FavoriteProductResponseDTO> list = favoriteProductService.getMyFavorites();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<FavoriteProductResponseDTO>> getFavoritesByUserId(
            @PathVariable Long userId) {
        List<FavoriteProductResponseDTO> list = favoriteProductService.getFavoritesByUserId(userId);
        return ResponseEntity.ok(list);
    }


    @GetMapping("/{favoriteId}")
    public ResponseEntity<FavoriteProductResponseDTO> getFavoriteById(
            @PathVariable @Min(1) Long favoriteId) {
        FavoriteProductResponseDTO dto = favoriteProductService.getFavoriteById(favoriteId);
        return ResponseEntity.ok(dto);
    }


    @DeleteMapping("/{favoriteId}")
    public ResponseEntity<Void> removeFavorite(
            @PathVariable @Min(1) Long favoriteId) {
        favoriteProductService.removeFavorite(favoriteId);
        return ResponseEntity.noContent().build();
    }
}
