package com.clothashe.clotashe_backend.controller.product;
import com.clothashe.clotashe_backend.model.dto.product.create.CreateColorRequestDTO;
import com.clothashe.clotashe_backend.model.dto.product.response.ColorResponseDTO;
import com.clothashe.clotashe_backend.model.dto.product.update.UpdateColorRequestDTO;
import com.clothashe.clotashe_backend.service.product.ColorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/colors")
@RequiredArgsConstructor
@Validated
public class ColorController {

    private final ColorService colorService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ColorResponseDTO> create(@Valid @RequestBody CreateColorRequestDTO dto) {
        ColorResponseDTO created = colorService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ColorResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateColorRequestDTO dto) {
        ColorResponseDTO updated = colorService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ColorResponseDTO> findById(@PathVariable Long id) {
        ColorResponseDTO dto = colorService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ColorResponseDTO>> findAll() {
        List<ColorResponseDTO> colors = colorService.findAll();
        return ResponseEntity.ok(colors);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        colorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
