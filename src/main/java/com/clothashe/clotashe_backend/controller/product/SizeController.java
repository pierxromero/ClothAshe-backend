package com.clothashe.clotashe_backend.controller.product;

import com.clothashe.clotashe_backend.exception.error.ApiError;
import com.clothashe.clotashe_backend.model.dto.product.create.CreateSizeRequestDTO;
import com.clothashe.clotashe_backend.model.dto.product.response.SizeResponseDTO;
import com.clothashe.clotashe_backend.model.dto.product.update.UpdateSizeRequestDTO;
import com.clothashe.clotashe_backend.service.product.SizeService;
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
@RequestMapping("/api/sizes")
@RequiredArgsConstructor
@Validated
@Tag(name = "Sizes", description = "Operations related to size management")
public class SizeController {

    private final SizeService sizeService;

    @Operation(
            summary = "Create a new size",
            description = "Allows an ADMIN to create a new size code and optional description.",
            security = @SecurityRequirement(name = "bearerAuth"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO containing code and optional description for the new size",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateSizeRequestDTO.class)
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Size created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SizeResponseDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Validation error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                            {
                              "message": "Invalid size code format",
                              "status": 400,
                              "errorCode": "VALIDATION_ERROR",
                              "path": "/api/sizes",
                              "timestamp": "2025-06-08T14:55:00"
                            }
                            """)
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
                              "path": "/api/sizes",
                              "timestamp": "2025-06-08T14:56:00"
                            }
                            """)
                    )
            ),
            @ApiResponse(responseCode = "409", description = "Size code already exists",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                            {
                              "message": "Size with code 'XL' already exists",
                              "status": 409,
                              "errorCode": "SIZE_CONFLICT",
                              "path": "/api/sizes",
                              "timestamp": "2025-06-08T14:57:00"
                            }
                            """)
                    )
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<SizeResponseDTO> create(
            @Valid @RequestBody CreateSizeRequestDTO dto
    ) {
        SizeResponseDTO created = sizeService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(
            summary = "Update an existing size",
            description = "Allows an ADMIN to update code and/or description of a size.",
            security = @SecurityRequirement(name = "bearerAuth"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO containing updated fields for the size",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UpdateSizeRequestDTO.class)
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Size updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SizeResponseDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Validation error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                            {
                              "message": "Invalid size update request",
                              "status": 400,
                              "errorCode": "VALIDATION_ERROR",
                              "path": "/api/sizes/1",
                              "timestamp": "2025-06-08T14:58:00"
                            }
                            """)
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
                              "path": "/api/sizes/1",
                              "timestamp": "2025-06-08T14:59:00"
                            }
                            """)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Size not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                            {
                              "message": "Size not found with id: 1",
                              "status": 404,
                              "errorCode": "SIZE_NOT_FOUND",
                              "path": "/api/sizes/1",
                              "timestamp": "2025-06-08T15:00:00"
                            }
                            """)
                    )
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<SizeResponseDTO> update(
            @Parameter(in = ParameterIn.PATH, description = "ID of the size to update", required = true)
            @PathVariable Long id,
            @Valid @RequestBody UpdateSizeRequestDTO dto
    ) {
        SizeResponseDTO updated = sizeService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Operation(
            summary = "Get size by ID",
            description = "Retrieves a specific size using its unique identifier.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Size found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SizeResponseDTO.class)
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
                              "path": "/api/sizes/3",
                              "timestamp": "2025-06-08T15:01:00"
                            }
                            """)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Size not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                            {
                              "message": "Size not found with id: 3",
                              "status": 404,
                              "errorCode": "SIZE_NOT_FOUND",
                              "path": "/api/sizes/3",
                              "timestamp": "2025-06-08T15:02:00"
                            }
                            """)
                    )
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<SizeResponseDTO> findById(
            @Parameter(in = ParameterIn.PATH, description = "ID of the size to retrieve", required = true)
            @PathVariable Long id
    ) {
        SizeResponseDTO dto = sizeService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "List all sizes",
            description = "Retrieves a list of all defined sizes (ADMIN only).",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of sizes returned",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = SizeResponseDTO.class))
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
                              "path": "/api/sizes",
                              "timestamp": "2025-06-08T15:03:00"
                            }
                            """)
                    )
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<SizeResponseDTO>> findAll() {
        List<SizeResponseDTO> list = sizeService.findAll();
        return ResponseEntity.ok(list);
    }

    @Operation(
            summary = "Delete a size",
            description = "Allows an ADMIN to delete a size by ID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Size deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied – ADMIN role required",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                            {
                              "message": "Access denied",
                              "status": 403,
                              "errorCode": "FORBIDDEN",
                              "path": "/api/sizes/4",
                              "timestamp": "2025-06-08T15:04:00"
                            }
                            """)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Size not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                            {
                              "message": "Size not found with id: 4",
                              "status": 404,
                              "errorCode": "SIZE_NOT_FOUND",
                              "path": "/api/sizes/4",
                              "timestamp": "2025-06-08T15:05:00"
                            }
                            """)
                    )
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(in = ParameterIn.PATH, description = "ID of the size to delete", required = true)
            @PathVariable Long id
    ) {
        sizeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}