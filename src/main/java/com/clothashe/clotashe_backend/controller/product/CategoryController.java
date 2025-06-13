package com.clothashe.clotashe_backend.controller.product;


import com.clothashe.clotashe_backend.model.dto.product.create.CreateCategoryRequestDTO;
import com.clothashe.clotashe_backend.model.dto.product.response.CategoryResponseDTO;
import com.clothashe.clotashe_backend.model.dto.product.update.UpdateCategoryRequestDTO;
import com.clothashe.clotashe_backend.service.product.CategoryService;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;


@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Validated
@Tag(name = "Categories", description = "Operations related to product categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(
            summary = "Create a new category",
            description = "Allows an ADMIN to create a new product category.",
            security = @SecurityRequirement(name = "bearerAuth"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Payload to create a new category",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateCategoryRequestDTO.class)
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Category created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CategoryResponseDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Validation error or missing fields",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Category name must not be blank",
                      "status": 400,
                      "errorCode": "BAD_REQUEST",
                      "path": "/api/categories",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "403", description = "Access denied - ADMIN role required",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Access denied",
                      "status": 403,
                      "errorCode": "FORBIDDEN",
                      "path": "/api/categories",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "409", description = "Category already exists",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "A category with the name 'T-shirts' already exists.",
                      "status": 409,
                      "errorCode": "CATEGORY_ALREADY_EXISTS",
                      "path": "/api/categories",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            )
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryResponseDTO> createCategory(
            @Valid @RequestBody CreateCategoryRequestDTO dto
    ) {
        CategoryResponseDTO created = categoryService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(
            summary = "Update an existing category",
            description = "Allows an ADMIN to update one or more fields of a category.",
            security = @SecurityRequirement(name = "bearerAuth"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Fields to update in the category",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UpdateCategoryRequestDTO.class)
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CategoryResponseDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid update data",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Category name must be at most 255 characters",
                      "status": 400,
                      "errorCode": "BAD_REQUEST",
                      "path": "/api/categories/5",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "403", description = "Access denied - ADMIN role required",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Access denied",
                      "status": 403,
                      "errorCode": "FORBIDDEN",
                      "path": "/api/categories/5",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Category not found with id: 5",
                      "status": 404,
                      "errorCode": "CATEGORY_NOT_FOUND",
                      "path": "/api/categories/5",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            )
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryResponseDTO> updateCategory(
            @Parameter(in = ParameterIn.PATH, description = "ID of the category to update", required = true)
            @PathVariable Long id,
            @Valid @RequestBody UpdateCategoryRequestDTO dto
    ) {
        CategoryResponseDTO updated = categoryService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Operation(
            summary = "Get category by ID",
            description = "Retrieves details of a specific category by its ID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CategoryResponseDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "403", description = "Access denied - ADMIN role required",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Access denied",
                      "status": 403,
                      "errorCode": "FORBIDDEN",
                      "path": "/api/categories/5",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Category not found with id: 5",
                      "status": 404,
                      "errorCode": "CATEGORY_NOT_FOUND",
                      "path": "/api/categories/5",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            )
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(
            @Parameter(in = ParameterIn.PATH, description = "ID of the category to retrieve", required = true)
            @PathVariable Long id
    ) {
        CategoryResponseDTO dto = categoryService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "List all categories",
            description = "Retrieves a list of all categories. ADMIN only.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of categories returned",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = CategoryResponseDTO.class))
                    )
            ),
            @ApiResponse(responseCode = "403", description = "Access denied - ADMIN role required",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Access denied",
                      "status": 403,
                      "errorCode": "FORBIDDEN",
                      "path": "/api/categories",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            )
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        List<CategoryResponseDTO> list = categoryService.findAll();
        return ResponseEntity.ok(list);
    }

    @Operation(
            summary = "Delete a category",
            description = "Allows an ADMIN to delete a category by its ID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied - ADMIN role required",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Access denied",
                      "status": 403,
                      "errorCode": "FORBIDDEN",
                      "path": "/api/categories/5",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Category not found with id: 5",
                      "status": 404,
                      "errorCode": "CATEGORY_NOT_FOUND",
                      "path": "/api/categories/5",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            )
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCategory(
            @Parameter(in = ParameterIn.PATH, description = "ID of the category to delete", required = true)
            @PathVariable Long id
    ) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}