package com.clothashe.clotashe_backend.controller.product;
import com.clothashe.clotashe_backend.model.dto.product.create.CreateProductRequestDTO;
import com.clothashe.clotashe_backend.model.dto.product.response.ProductResponseDTO;
import com.clothashe.clotashe_backend.model.dto.product.update.UpdateProductRequestDTO;
import com.clothashe.clotashe_backend.service.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Validated
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponseDTO> create(
            @Valid @RequestBody CreateProductRequestDTO dto) {
        ProductResponseDTO created = productService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> findAll() {
        List<ProductResponseDTO> list = productService.findAll();
        return ResponseEntity.ok(list);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(
            @PathVariable Long id) {
        ProductResponseDTO dto = productService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProductRequestDTO dto) {
        ProductResponseDTO updated = productService.update(id, dto);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.findByCategoryId(categoryId));
    }

    @GetMapping("/by-price")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByPriceRange(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice) {
        return ResponseEntity.ok(productService.findByPriceRange(minPrice, maxPrice));
    }

    @GetMapping("/by-stock")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByStockAvailability(
            @RequestParam boolean onlyWithStock) {
        return ResponseEntity.ok(productService.findByStockAvailability(onlyWithStock));
    }

    @GetMapping("/top-rated")
    public ResponseEntity<List<ProductResponseDTO>> getTop10RatedProducts() {
        List<ProductResponseDTO> topProducts = productService.findTop10ByRating();
        return ResponseEntity.ok(topProducts);
    }
}