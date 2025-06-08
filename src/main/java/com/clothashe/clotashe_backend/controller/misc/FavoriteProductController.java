package com.clothashe.clotashe_backend.controller.misc;



import com.clothashe.clotashe_backend.exception.error.ApiError;
import com.clothashe.clotashe_backend.model.dto.user.create.CreateFavoriteProductRequestDTO;
import com.clothashe.clotashe_backend.model.dto.user.response.FavoriteProductResponseDTO;
import com.clothashe.clotashe_backend.service.misc.FavoriteProductService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;
@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
@Validated
@Tag(name = "Favorite Products", description = "API for managing users' favorite products")
public class FavoriteProductController {

    private final FavoriteProductService favoriteProductService;

    @Operation(
            summary = "Add a product to favorites",
            description = "Marks a product as favorite for the authenticated user. " +
                    "Returns the favorite record created.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Favorite added successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = FavoriteProductResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "404", description = "Product not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "409", description = "Product is already in favorites",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized",
                            content = @Content),
            }
    )
    @PostMapping
    public ResponseEntity<FavoriteProductResponseDTO> addFavorite(
            @Valid @RequestBody CreateFavoriteProductRequestDTO dto) {
        FavoriteProductResponseDTO created = favoriteProductService.addFavorite(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(
            summary = "Get all favorites of the authenticated user",
            description = "Returns a list of products marked as favorites by the authenticated user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of favorite products",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = FavoriteProductResponseDTO.class)))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
            }
    )
    @GetMapping("/me")
    public ResponseEntity<List<FavoriteProductResponseDTO>> getMyFavorites() {
        List<FavoriteProductResponseDTO> list = favoriteProductService.getMyFavorites();
        return ResponseEntity.ok(list);
    }

    @Operation(
            summary = "Get favorites by user ID (admin only)",
            description = "Returns a list of favorites for the specified user ID. " +
                    "Requires ADMIN role.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of user's favorite products",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = FavoriteProductResponseDTO.class)))),
                    @ApiResponse(responseCode = "403", description = "Forbidden - requires ADMIN role",
                            content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
            }
    )
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<FavoriteProductResponseDTO>> getFavoritesByUserId(
            @PathVariable Long userId) {
        List<FavoriteProductResponseDTO> list = favoriteProductService.getFavoritesByUserId(userId);
        return ResponseEntity.ok(list);
    }

    @Operation(
            summary = "Get favorite by ID",
            description = "Returns the favorite product record by its ID. The authenticated user " +
                    "can only access their own favorites.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Favorite product found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = FavoriteProductResponseDTO.class))),
                    @ApiResponse(responseCode = "403", description = "Forbidden - access denied",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "Favorite not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
            }
    )
    @GetMapping("/{favoriteId}")
    public ResponseEntity<FavoriteProductResponseDTO> getFavoriteById(
            @PathVariable @Min(1) Long favoriteId) {
        FavoriteProductResponseDTO dto = favoriteProductService.getFavoriteById(favoriteId);
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "Remove a favorite product",
            description = "Deletes a favorite product record by ID. Only the owner user can delete their favorites.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Favorite removed successfully"),
                    @ApiResponse(responseCode = "403", description = "Forbidden - access denied",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "Favorite not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
            }
    )
    @DeleteMapping("/{favoriteId}")
    public ResponseEntity<Void> removeFavorite(
            @PathVariable @Min(1) Long favoriteId) {
        favoriteProductService.removeFavorite(favoriteId);
        return ResponseEntity.noContent().build();
    }
}