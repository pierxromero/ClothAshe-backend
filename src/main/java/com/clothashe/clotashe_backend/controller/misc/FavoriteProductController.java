package com.clothashe.clotashe_backend.controller.misc;



import com.clothashe.clotashe_backend.exception.error.ApiError;
import com.clothashe.clotashe_backend.model.dto.user.create.CreateFavoriteProductRequestDTO;
import com.clothashe.clotashe_backend.model.dto.user.response.FavoriteProductResponseDTO;
import com.clothashe.clotashe_backend.service.misc.FavoriteProductService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Add a product to favorites", description = "Marks a product as favorite for the authenticated user.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Favorite added successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FavoriteProductResponseDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Product ID must not be null",
                      "status": 400,
                      "errorCode": "BAD_REQUEST",
                      "path": "/api/favorites",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Product not found with ID 999",
                      "status": 404,
                      "errorCode": "NOT_FOUND",
                      "path": "/api/favorites",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "409", description = "Product is already in favorites",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Product 42 is already marked as favorite",
                      "status": 409,
                      "errorCode": "CONFLICT",
                      "path": "/api/favorites",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Authentication required",
                      "status": 401,
                      "errorCode": "UNAUTHORIZED",
                      "path": "/api/favorites",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<FavoriteProductResponseDTO> addFavorite(
            @Valid @RequestBody CreateFavoriteProductRequestDTO dto) {
        FavoriteProductResponseDTO created = favoriteProductService.addFavorite(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Get all favorites of the authenticated user", description = "Returns a list of products marked as favorites by the user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of favorite products",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = FavoriteProductResponseDTO.class)))
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Authentication required",
                      "status": 401,
                      "errorCode": "UNAUTHORIZED",
                      "path": "/api/favorites/me",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            )
    })
    @GetMapping("/me")
    public ResponseEntity<List<FavoriteProductResponseDTO>> getMyFavorites() {
        List<FavoriteProductResponseDTO> list = favoriteProductService.getMyFavorites();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Get favorites by user ID (admin only)", description = "Requires ADMIN role.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of user's favorite products",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = FavoriteProductResponseDTO.class)))
            ),
            @ApiResponse(responseCode = "403", description = "Forbidden - requires ADMIN role",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Access denied",
                      "status": 403,
                      "errorCode": "FORBIDDEN",
                      "path": "/api/favorites/user/5",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Authentication required",
                      "status": 401,
                      "errorCode": "UNAUTHORIZED",
                      "path": "/api/favorites/user/5",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            )
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FavoriteProductResponseDTO>> getFavoritesByUserId(
            @PathVariable Long userId) {
        List<FavoriteProductResponseDTO> list = favoriteProductService.getFavoritesByUserId(userId);
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Get favorite by ID", description = "Returns the favorite record. User can only access their own.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Favorite product found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FavoriteProductResponseDTO.class))
            ),
            @ApiResponse(responseCode = "403", description = "Forbidden - access denied",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Access denied",
                      "status": 403,
                      "errorCode": "FORBIDDEN",
                      "path": "/api/favorites/10",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Favorite not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Favorite not found with ID 10",
                      "status": 404,
                      "errorCode": "NOT_FOUND",
                      "path": "/api/favorites/10",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Authentication required",
                      "status": 401,
                      "errorCode": "UNAUTHORIZED",
                      "path": "/api/favorites/10",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            )
    })
    @GetMapping("/{favoriteId}")
    public ResponseEntity<FavoriteProductResponseDTO> getFavoriteById(
            @PathVariable @Min(1) Long favoriteId) {
        FavoriteProductResponseDTO dto = favoriteProductService.getFavoriteById(favoriteId);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Remove a favorite product", description = "Deletes a favorite by ID. Only owner can delete.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Favorite removed successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden - access denied",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Access denied",
                      "status": 403,
                      "errorCode": "FORBIDDEN",
                      "path": "/api/favorites/10",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Favorite not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Favorite not found with ID 10",
                      "status": 404,
                      "errorCode": "NOT_FOUND",
                      "path": "/api/favorites/10",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Authentication required",
                      "status": 401,
                      "errorCode": "UNAUTHORIZED",
                      "path": "/api/favorites/10",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            )
    })
    @DeleteMapping("/{favoriteId}")
    public ResponseEntity<Void> removeFavorite(
            @PathVariable @Min(1) Long favoriteId) {
        favoriteProductService.removeFavorite(favoriteId);
        return ResponseEntity.noContent().build();
    }
}