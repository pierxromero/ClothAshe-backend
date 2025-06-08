package com.clothashe.clotashe_backend.controller.product;

import com.clothashe.clotashe_backend.model.dto.product.create.CreateSizeRequestDTO;
import com.clothashe.clotashe_backend.model.dto.product.response.SizeResponseDTO;
import com.clothashe.clotashe_backend.model.dto.product.update.UpdateSizeRequestDTO;
import com.clothashe.clotashe_backend.service.product.SizeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sizes")
@RequiredArgsConstructor
@Validated
public class SizeController {

    private final SizeService sizeService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SizeResponseDTO> create(@Valid @RequestBody CreateSizeRequestDTO dto) {
        SizeResponseDTO created = sizeService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SizeResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateSizeRequestDTO dto) {
        SizeResponseDTO updated = sizeService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SizeResponseDTO> findById(@PathVariable Long id) {
        SizeResponseDTO dto = sizeService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SizeResponseDTO>> findAll() {
        List<SizeResponseDTO> list = sizeService.findAll();
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        sizeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}