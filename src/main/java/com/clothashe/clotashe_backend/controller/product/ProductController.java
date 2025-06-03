package com.clothashe.clotashe_backend.controller.product;
import com.clothashe.clotashe_backend.service.product.impl.ProductServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "CRUD operations for managing products")
public class ProductController {

    private final ProductServiceImpl productService;











//    @Operation(
//            summary = "Retrieve all products",
//            description = "Returns a list of all available products."
//    )
//    @ApiResponse(
//            responseCode = "200",
//            description = "List of products retrieved successfully",
//            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))
//    )
//    @GetMapping
//    public ResponseEntity<List<ProductDTO>> getAllProducts() {
//        List<ProductDTO> products = productService.getAll();
//        return ResponseEntity.ok(products);
//    }
//
//    @Operation(
//            summary = "Retrieve a product by ID",
//            description = "Returns the product that matches the provided ID."
//    )
//    @ApiResponses(value = {
//            @ApiResponse(
//                    responseCode = "200",
//                    description = "Product found",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))
//            ),
//            @ApiResponse(
//                    responseCode = "404",
//                    description = "Product not found",
//                    content = @Content
//            )
//    })
//    @GetMapping("/{id}")
//    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
//        ProductDTO product = productService.getById(id);
//        return ResponseEntity.ok(product);
//    }
//
//    @Operation(
//            summary = "Create a new product",
//            description = "Creates a new product using the provided valid product data."
//    )
//    @ApiResponses(value = {
//            @ApiResponse(
//                    responseCode = "200",
//                    description = "Product created successfully",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))
//            ),
//            @ApiResponse(
//                    responseCode = "400",
//                    description = "Invalid input data",
//                    content = @Content
//            )
//    })
//    @PostMapping
//    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
//        ProductDTO createdProduct = productService.create(productDTO);
//        return ResponseEntity.ok(createdProduct);
//    }
//
//    @Operation(
//            summary = "Update an existing product",
//            description = "Updates the product identified by the given ID using the provided data."
//    )
//    @ApiResponses(value = {
//            @ApiResponse(
//                    responseCode = "200",
//                    description = "Product updated successfully",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))
//            ),
//            @ApiResponse(
//                    responseCode = "400",
//                    description = "Invalid input data",
//                    content = @Content
//            ),
//            @ApiResponse(
//                    responseCode = "404",
//                    description = "Product not found",
//                    content = @Content
//            )
//    })
//    @PutMapping("/{id}")
//    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
//        ProductDTO updatedProduct = productService.update(id, productDTO);
//        return ResponseEntity.ok(updatedProduct);
//    }
//
//    @Operation(
//            summary = "Delete a product",
//            description = "Deletes the product identified by the given ID."
//    )
//    @ApiResponses(value = {
//            @ApiResponse(
//                    responseCode = "204",
//                    description = "Product deleted successfully",
//                    content = @Content
//            ),
//            @ApiResponse(
//                    responseCode = "404",
//                    description = "Product not found",
//                    content = @Content
//            )
//    })
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
//        productService.delete(id);
//        return ResponseEntity.noContent().build();
//    }
}