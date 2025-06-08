package com.clothashe.clotashe_backend.controller.product;

import com.clothashe.clotashe_backend.model.dto.product.create.CreateBrandRequestDTO;
import com.clothashe.clotashe_backend.model.dto.product.response.BrandResponseDTO;
import com.clothashe.clotashe_backend.model.dto.product.update.UpdateBrandRequestDTO;
import com.clothashe.clotashe_backend.service.product.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.clothashe.clotashe_backend.exception.error.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
@Validated
@Tag(name = "Brands", description = "Operations for managing product brands")
public class BrandController {

    private final BrandService brandService;

    @Operation(
            summary = "Create a new brand",
            description = "Creates a new brand. Brand names must be unique (case-insensitive).",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Brand created successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BrandResponseDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Validation error in request body",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class))
            ),
            @ApiResponse(responseCode = "409", description = "Brand with that name already exists",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class))
            ),
            @ApiResponse(responseCode = "403", description = "Access denied—ADMIN role required",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class))
            )
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Payload for creating a brand",
            required = true,
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CreateBrandRequestDTO.class))
    )
    @PostMapping
    public ResponseEntity<BrandResponseDTO> createBrand(
            @Valid @RequestBody CreateBrandRequestDTO dto
    ) {
        BrandResponseDTO created = brandService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(
            summary = "Update an existing brand",
            description = "Updates name and/or description of a brand by ID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Brand updated successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BrandResponseDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Validation error in request body",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class))
            ),
            @ApiResponse(responseCode = "404", description = "Brand not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class))
            ),
            @ApiResponse(responseCode = "409", description = "New name conflicts with existing brand",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class))
            ),
            @ApiResponse(responseCode = "403", description = "Access denied—ADMIN role required",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class))
            )
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Payload for updating a brand",
            required = true,
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = UpdateBrandRequestDTO.class))
    )
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BrandResponseDTO> updateBrand(
            @Parameter(description = "ID of the brand to update", example = "10", required = true)
            @PathVariable Long id,
            @Valid @RequestBody UpdateBrandRequestDTO dto
    ) {
        BrandResponseDTO updated = brandService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Operation(
            summary = "Get a brand by ID",
            description = "Retrieves brand details by its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Brand retrieved",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BrandResponseDTO.class))
            ),
            @ApiResponse(responseCode = "404", description = "Brand not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<BrandResponseDTO> getBrandById(
            @Parameter(description = "ID of the brand", example = "10", required = true)
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(brandService.findById(id));
    }

    @Operation(
            summary = "List all brands",
            description = "Returns a list of all brands in the system."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of brands",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = BrandResponseDTO.class))
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<BrandResponseDTO>> getAllBrands() {
        return ResponseEntity.ok(brandService.findAll());
    }

    @Operation(
            summary = "Delete a brand",
            description = "Deletes a brand by its ID. All associated products must be unlinked first.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Brand deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Brand not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class))
            ),
            @ApiResponse(responseCode = "403", description = "Access denied—ADMIN role required",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class))
            )
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBrand(
            @Parameter(description = "ID of the brand to delete", example = "10", required = true)
            @PathVariable Long id
    ) {
        brandService.delete(id);
        return ResponseEntity.noContent().build();
    }
}