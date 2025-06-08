package com.clothashe.clotashe_backend.controller.misc;

import com.clothashe.clotashe_backend.model.dto.user.create.CreateAddressRequestDTO;
import com.clothashe.clotashe_backend.model.dto.user.response.AddressResponseDTO;
import com.clothashe.clotashe_backend.model.dto.user.update.UpdateAddressRequestDTO;
import com.clothashe.clotashe_backend.service.misc.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressResponseDTO> createAddress(@RequestBody @Valid CreateAddressRequestDTO requestDTO) {
        AddressResponseDTO created = addressService.createAddress(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> getAddressById(@PathVariable Long id) {
        AddressResponseDTO dto = addressService.getAddressById(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> updateAddress(
            @PathVariable Long id,
            @RequestBody @Valid UpdateAddressRequestDTO updateDTO) {
        AddressResponseDTO updated = addressService.updateAddress(id, updateDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<List<AddressResponseDTO>> getAllMyAddresses() {
        return ResponseEntity.ok(addressService.getAllMyAddresses());
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AddressResponseDTO>> getAllAddressesByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(addressService.getAllAddressesByUserId(userId));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AddressResponseDTO>> getAllAddresses() {
        return ResponseEntity.ok(addressService.getAllAddresses());
    }
}