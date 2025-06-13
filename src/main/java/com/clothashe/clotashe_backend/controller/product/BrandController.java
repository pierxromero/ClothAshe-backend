package com.clothashe.clotashe_backend.controller.product;
import com.clothashe.clotashe_backend.exception.error.ApiError;
import com.clothashe.clotashe_backend.model.dto.product.create.CreateBrandRequestDTO;
import com.clothashe.clotashe_backend.model.dto.product.response.BrandResponseDTO;
import com.clothashe.clotashe_backend.model.dto.product.update.UpdateBrandRequestDTO;
import com.clothashe.clotashe_backend.service.product.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
@Validated
@Tag(name = "Brands", description = "Operations related to brand management")
public class BrandController {

    private final BrandService brandService;

    @Operation(
            summary = "Create a new brand",
            description = "Allows an ADMIN to create a new brand entry.",
            security = @SecurityRequirement(name = "bearerAuth"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO containing name and description for the new brand",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateBrandRequestDTO.class)
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Brand created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BrandResponseDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Validation error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                            {
                              "message": "Brand name must not be blank",
                              "status": 400,
                              "errorCode": "VALIDATION_ERROR",
                              "path": "/api/brands",
                              "timestamp": "2025-06-13T08:00:00"
                            }
                            """
                            )
                    )
            ),
            @ApiResponse(responseCode = "403", description = "Access denied – ADMIN role required",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                            {
                              "message": "Access denied",
                              "status": 403,
                              "errorCode": "FORBIDDEN",
                              "path": "/api/brands",
                              "timestamp": "2025-06-13T08:00:00"
                            }
                            """
                            )
                    )
            ),
            @ApiResponse(responseCode = "409", description = "Brand already exists",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                            {
                              "message": "Brand with name 'Nike' already exists",
                              "status": 409,
                              "errorCode": "BRAND_ALREADY_EXISTS",
                              "path": "/api/brands",
                              "timestamp": "2025-06-13T08:00:00"
                            }
                            """
                            )
                    )
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<BrandResponseDTO> create(
            @Valid @RequestBody CreateBrandRequestDTO dto
    ) {
        BrandResponseDTO created = brandService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(
            summary = "Update an existing brand",
            description = "Allows an ADMIN to update the name and/or description of a brand.",
            security = @SecurityRequirement(name = "bearerAuth"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO containing updated fields for the brand",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UpdateBrandRequestDTO.class)
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Brand updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BrandResponseDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Validation error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                            {
                              "message": "Brand name cannot be blank",
                              "status": 400,
                              "errorCode": "VALIDATION_ERROR",
                              "path": "/api/brands/2",
                              "timestamp": "2025-06-13T08:00:00"
                            }
                            """
                            )
                    )
            ),
            @ApiResponse(responseCode = "403", description = "Access denied – ADMIN role required",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                            {
                              "message": "Access denied",
                              "status": 403,
                              "errorCode": "FORBIDDEN",
                              "path": "/api/brands/2",
                              "timestamp": "2025-06-13T08:00:00"
                            }
                            """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Brand not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                            {
                              "message": "Brand not found with id: 2",
                              "status": 404,
                              "errorCode": "BRAND_NOT_FOUND",
                              "path": "/api/brands/2",
                              "timestamp": "2025-06-13T08:00:00"
                            }
                            """
                            )
                    )
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<BrandResponseDTO> update(
            @Parameter(in = ParameterIn.PATH, description = "ID of the brand to update", required = true)
            @PathVariable Long id,
            @Valid @RequestBody UpdateBrandRequestDTO dto
    ) {
        BrandResponseDTO updated = brandService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Operation(
            summary = "Get brand by ID",
            description = "Retrieves a specific brand using its unique identifier.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Brand found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BrandResponseDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "403", description = "Access denied – ADMIN role required",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                            {
                              "message": "Access denied",
                              "status": 403,
                              "errorCode": "FORBIDDEN",
                              "path": "/api/brands/5",
                              "timestamp": "2025-06-13T08:00:00"
                            }
                            """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Brand not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                            {
                              "message": "Brand not found with id: 5",
                              "status": 404,
                              "errorCode": "BRAND_NOT_FOUND",
                              "path": "/api/brands/5",
                              "timestamp": "2025-06-13T08:00:00"
                            }
                            """
                            )
                    )
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<BrandResponseDTO> findById(
            @Parameter(in = ParameterIn.PATH, description = "ID of the brand to retrieve", required = true)
            @PathVariable Long id
    ) {
        BrandResponseDTO dto = brandService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "List all brands",
            description = "Retrieves a list of all brands in the system (ADMIN only).",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of brands returned",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = BrandResponseDTO.class))
                    )
            ),
            @ApiResponse(responseCode = "403", description = "Access denied – ADMIN role required",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                            {
                              "message": "Access denied",
                              "status": 403,
                              "errorCode": "FORBIDDEN",
                              "path": "/api/brands",
                              "timestamp": "2025-06-13T08:00:00"
                            }
                            """
                            )
                    )
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<BrandResponseDTO>> findAll() {
        List<BrandResponseDTO> list = brandService.findAll();
        return ResponseEntity.ok(list);
    }

    @Operation(
            summary = "Delete a brand",
            description = "Allows an ADMIN to delete a brand by ID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Brand deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied – ADMIN role required",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                            {
                              "message": "Access denied",
                              "status": 403,
                              "errorCode": "FORBIDDEN",
                              "path": "/api/brands/3",
                              "timestamp": "2025-06-13T08:00:00"
                            }
                            """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Brand not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                            {
                              "message": "Brand not found with id: 3",
                              "status": 404,
                              "errorCode": "BRAND_NOT_FOUND",
                              "path": "/api/brands/3",
                              "timestamp": "2025-06-13T08:00:00"
                            }
                            """
                            )
                    )
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(in = ParameterIn.PATH, description = "ID of the brand to delete", required = true)
            @PathVariable Long id
    ) {
        brandService.delete(id);
        return ResponseEntity.noContent().build();
    }
}