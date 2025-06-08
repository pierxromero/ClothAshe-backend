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

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
@Validated
public class BrandController {

    private final BrandService brandService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BrandResponseDTO> createBrand(@Valid @RequestBody CreateBrandRequestDTO dto) {
        BrandResponseDTO response = brandService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BrandResponseDTO> updateBrand(@PathVariable Long id,
                                                        @Valid @RequestBody UpdateBrandRequestDTO dto) {
        BrandResponseDTO response = brandService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BrandResponseDTO> getBrandById(@PathVariable Long id) {
        return ResponseEntity.ok(brandService.findById(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BrandResponseDTO>> getAllBrands() {
        return ResponseEntity.ok(brandService.findAll());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
        brandService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
