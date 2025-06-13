package com.clothashe.clotashe_backend.controller.product;


import com.clothashe.clotashe_backend.exception.error.ApiError;
import com.clothashe.clotashe_backend.model.dto.product.create.CreateProductRequestDTO;
import com.clothashe.clotashe_backend.model.dto.product.response.ProductResponseDTO;
import com.clothashe.clotashe_backend.model.dto.product.update.UpdateProductRequestDTO;
import com.clothashe.clotashe_backend.service.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Validated
@Tag(name = "Products", description = "Operations related to product management")
public class ProductController {

    private final ProductService productService;

    @Operation(
            summary = "Create a new product",
            description = "Allows an ADMIN to create a new product with category, size, color and brand associations.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProductResponseDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Validation error or missing fields",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                            {
                              "message": "Category with ID 99 does not exist",
                              "status": 400,
                              "errorCode": "CATEGORY_NOT_FOUND",
                              "path": "/api/products",
                              "timestamp": "2025-06-08T10:20:00"
                            }
                            """))
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
                              "path": "/api/products",
                              "timestamp": "2025-06-08T10:21:00"
                            }
                            """))
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(
            @Valid @RequestBody CreateProductRequestDTO dto
    ) {
        ProductResponseDTO created = productService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(
            summary = "Get all products",
            description = "Retrieves a paginated list of all products, regardless of status."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page of products returned",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Page.class)
                    )
            )
    })
    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> findAll(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Pageable pageable = PageRequest.of(page, size,
                sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending());
        Page<ProductResponseDTO> productsPage = productService.findAll(pageable);
        return ResponseEntity.ok(productsPage);
    }

    @Operation(
            summary = "Get product by ID",
            description = "Retrieves a single product by its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProductResponseDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                            {
                              "message": "Product not found with id: 42",
                              "status": 404,
                              "errorCode": "PRODUCT_NOT_FOUND",
                              "path": "/api/products/42",
                              "timestamp": "2025-06-08T10:23:00"
                            }
                            """))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(
            @PathVariable Long id
    ) {
        ProductResponseDTO dto = productService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "Update an existing product",
            description = "Allows an ADMIN to update product fields (any subset).",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProductResponseDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid update data",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                            {
                              "message": "Brand with ID 77 does not exist",
                              "status": 400,
                              "errorCode": "BRAND_NOT_FOUND",
                              "path": "/api/products/12",
                              "timestamp": "2025-06-08T10:24:00"
                            }
                            """))
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
                              "path": "/api/products/12",
                              "timestamp": "2025-06-08T10:25:00"
                            }
                            """))
            ),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                            {
                              "message": "Product not found with id: 12",
                              "status": 404,
                              "errorCode": "PRODUCT_NOT_FOUND",
                              "path": "/api/products/12",
                              "timestamp": "2025-06-08T10:26:00"
                            }
                            """))
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProductRequestDTO dto
    ) {
        ProductResponseDTO updated = productService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Operation(
            summary = "Delete a product",
            description = "Allows an ADMIN to delete a product by its ID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied - ADMIN role required",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                            {
                              "message": "Access denied",
                              "status": 403,
                              "errorCode": "FORBIDDEN",
                              "path": "/api/products/3",
                              "timestamp": "2025-06-08T10:27:00"
                            }
                            """))
            ),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                            {
                              "message": "Product not found with id: 3",
                              "status": 404,
                              "errorCode": "PRODUCT_NOT_FOUND",
                              "path": "/api/products/3",
                              "timestamp": "2025-06-08T10:28:00"
                            }
                            """))
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Get products by category",
            description = "Retrieves a paginated list of products belonging to the given category."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page of products returned",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Page.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                        {
                          "message": "Category not found with id: 15",
                          "status": 404,
                          "errorCode": "CATEGORY_NOT_FOUND",
                          "path": "/api/products/by-category/15",
                          "timestamp": "2025-06-08T10:29:00"
                        }
                        """))
            )
    })
    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<Page<ProductResponseDTO>> getProductsByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Pageable pageable = PageRequest.of(page, size,
                sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending());
        Page<ProductResponseDTO> productsPage = productService.findByCategoryId(categoryId, pageable);
        return ResponseEntity.ok(productsPage);
    }

    @Operation(
            summary = "Get products by price range",
            description = "Retrieves a paginated list of products whose price is between `minPrice` and `maxPrice`."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page of products returned",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Page.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid price range",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                        {
                          "message": "minPrice must be less than or equal to maxPrice",
                          "status": 400,
                          "errorCode": "INVALID_PRICE_RANGE",
                          "path": "/api/products/by-price",
                          "timestamp": "2025-06-08T10:30:00"
                        }
                        """))
            )
    })
    @GetMapping("/by-price")
    public ResponseEntity<Page<ProductResponseDTO>> getProductsByPriceRange(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size,
            @RequestParam(defaultValue = "price") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Pageable pageable = PageRequest.of(page, size,
                sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending());
        Page<ProductResponseDTO> productsPage = productService.findByPriceRange(minPrice, maxPrice, pageable);
        return ResponseEntity.ok(productsPage);
    }

    @Operation(
            summary = "Get products by stock availability",
            description = "Retrieves a paginated list of products filtered by stock availability. Set `onlyWithStock=true` to get only those with stock > 0."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page of products returned",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Page.class)
                    )
            )
    })
    @GetMapping("/by-stock")
    public ResponseEntity<Page<ProductResponseDTO>> getProductsByStockAvailability(
            @RequestParam boolean onlyWithStock,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Pageable pageable = PageRequest.of(page, size,
                sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending());
        Page<ProductResponseDTO> productsPage = productService.findByStockAvailability(onlyWithStock, pageable);
        return ResponseEntity.ok(productsPage);
    }

    @Operation(
            summary = "Get top 10 rated products",
            description = "Retrieves the ten highest-rated products in the catalog."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Top rated products returned",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(
                                    schema = @Schema(implementation = ProductResponseDTO.class)
                            )
                    )
            )
    })
    @GetMapping("/top-rated")
    public ResponseEntity<List<ProductResponseDTO>> getTop10RatedProducts() {
        return ResponseEntity.ok(productService.findTop10ByRating());
    }
}
