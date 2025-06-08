package com.clothashe.clotashe_backend.controller.misc;

import com.clothashe.clotashe_backend.exception.error.ApiError;
import com.clothashe.clotashe_backend.model.dto.user.create.CreateAddressRequestDTO;
import com.clothashe.clotashe_backend.model.dto.user.response.AddressResponseDTO;
import com.clothashe.clotashe_backend.model.dto.user.update.UpdateAddressRequestDTO;
import com.clothashe.clotashe_backend.service.misc.AddressService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
@Tag(name = "Addresses", description = "Operations related to user addresses")
public class AddressController {

    private final AddressService addressService;

    @Operation(
            summary = "Create a new address for the authenticated user",
            description = "Allows any authenticated user to create a new address. The address will be associated with their account."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Address successfully created",
                    content = @Content(schema = @Schema(implementation = AddressResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid address data",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @PostMapping
    public ResponseEntity<AddressResponseDTO> createAddress(
            @RequestBody @Valid CreateAddressRequestDTO requestDTO) {
        AddressResponseDTO created = addressService.createAddress(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(
            summary = "Get an address by its ID",
            description = "Returns the address with the specified ID. The user must own the address or be an admin."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address found",
                    content = @Content(schema = @Schema(implementation = AddressResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Access denied (not owner or admin)",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Address not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> getAddressById(@PathVariable Long id) {
        AddressResponseDTO dto = addressService.getAddressById(id);
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "Update an existing address",
            description = "Allows the owner or an admin to update the fields of an address."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address successfully updated",
                    content = @Content(schema = @Schema(implementation = AddressResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid address data",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "403", description = "Access denied",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Address not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> updateAddress(
            @PathVariable Long id,
            @RequestBody @Valid UpdateAddressRequestDTO updateDTO) {
        AddressResponseDTO updated = addressService.updateAddress(id, updateDTO);
        return ResponseEntity.ok(updated);
    }

    @Operation(
            summary = "Delete an address",
            description = "Deletes an address if the user is the owner or an admin. This does not affect past orders."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Address successfully deleted"),
            @ApiResponse(responseCode = "403", description = "Access denied",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Address not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Get all addresses of the authenticated user",
            description = "Returns a list of all addresses associated with the current user."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Addresses retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AddressResponseDTO.class)))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @GetMapping("/me")
    public ResponseEntity<List<AddressResponseDTO>> getAllMyAddresses() {
        return ResponseEntity.ok(addressService.getAllMyAddresses());
    }

    @Operation(
            summary = "Get all addresses for a specific user (Admin only)",
            description = "Allows administrators to retrieve all addresses linked to a user ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Addresses retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AddressResponseDTO.class)))),
            @ApiResponse(responseCode = "403", description = "Access denied (not admin)",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AddressResponseDTO>> getAllAddressesByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(addressService.getAllAddressesByUserId(userId));
    }

    @Operation(
            summary = "Get all addresses in the system (Admin only)",
            description = "Returns all addresses stored in the database. Requires admin privileges."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All addresses retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AddressResponseDTO.class)))),
            @ApiResponse(responseCode = "403", description = "Access denied (not admin)",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<AddressResponseDTO>> getAllAddresses() {
        return ResponseEntity.ok(addressService.getAllAddresses());
    }
}