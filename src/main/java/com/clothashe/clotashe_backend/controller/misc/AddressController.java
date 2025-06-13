package com.clothashe.clotashe_backend.controller.misc;

import com.clothashe.clotashe_backend.exception.error.ApiError;
import com.clothashe.clotashe_backend.model.dto.user.create.CreateAddressRequestDTO;
import com.clothashe.clotashe_backend.model.dto.user.response.AddressResponseDTO;
import com.clothashe.clotashe_backend.model.dto.user.update.UpdateAddressRequestDTO;
import com.clothashe.clotashe_backend.service.misc.AddressService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
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
@Validated
@Tag(name = "Addresses", description = "Operations related to user addresses")
public class AddressController {

    private final AddressService addressService;

    @Operation(summary = "Create a new address", description = "Create an address for the authenticated user.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Address successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AddressResponseDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid address data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Street is required",
                      "status": 400,
                      "errorCode": "BAD_REQUEST",
                      "path": "/api/addresses",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Authentication required",
                      "status": 401,
                      "errorCode": "UNAUTHORIZED",
                      "path": "/api/addresses",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<AddressResponseDTO> createAddress(
            @Valid @RequestBody CreateAddressRequestDTO requestDTO
    ) {
        AddressResponseDTO created = addressService.createAddress(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Get address by ID", description = "Returns the address. Must be owner or admin.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Address found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AddressResponseDTO.class))
            ),
            @ApiResponse(responseCode = "403", description = "Access denied",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Access denied",
                      "status": 403,
                      "errorCode": "FORBIDDEN",
                      "path": "/api/addresses/42",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Address not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Address not found",
                      "status": 404,
                      "errorCode": "NOT_FOUND",
                      "path": "/api/addresses/42",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> getAddressById(@PathVariable Long id) {
        return ResponseEntity.ok(addressService.getAddressById(id));
    }

    @Operation(summary = "Update an address", description = "Update fields of an address. Must be owner or admin.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Address successfully updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AddressResponseDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid address data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "City must not be blank",
                      "status": 400,
                      "errorCode": "BAD_REQUEST",
                      "path": "/api/addresses/42",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "403", description = "Access denied",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Access denied",
                      "status": 403,
                      "errorCode": "FORBIDDEN",
                      "path": "/api/addresses/42",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Address not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Address not found",
                      "status": 404,
                      "errorCode": "NOT_FOUND",
                      "path": "/api/addresses/42",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> updateAddress(
            @PathVariable Long id,
            @Valid @RequestBody UpdateAddressRequestDTO updateDTO
    ) {
        return ResponseEntity.ok(addressService.updateAddress(id, updateDTO));
    }

    @Operation(summary = "Delete an address", description = "Deletes an address. Must be owner or admin.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Address successfully deleted"),
            @ApiResponse(responseCode = "403", description = "Access denied",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Access denied",
                      "status": 403,
                      "errorCode": "FORBIDDEN",
                      "path": "/api/addresses/42",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Address not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Address not found",
                      "status": 404,
                      "errorCode": "NOT_FOUND",
                      "path": "/api/addresses/42",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "List my addresses", description = "Returns all addresses of the authenticated user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Addresses retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AddressResponseDTO.class)))
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Authentication required",
                      "status": 401,
                      "errorCode": "UNAUTHORIZED",
                      "path": "/api/addresses/me",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            )
    })
    @GetMapping("/me")
    public ResponseEntity<List<AddressResponseDTO>> getAllMyAddresses() {
        return ResponseEntity.ok(addressService.getAllMyAddresses());
    }

    @Operation(summary = "List addresses by user", description = "Returns all addresses for a given user. ADMIN only.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Addresses retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AddressResponseDTO.class)))
            ),
            @ApiResponse(responseCode = "403", description = "Access denied",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Access denied",
                      "status": 403,
                      "errorCode": "FORBIDDEN",
                      "path": "/api/addresses/user/10",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            )
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AddressResponseDTO>> getAllAddressesByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(addressService.getAllAddressesByUserId(userId));
    }

    @Operation(summary = "List all addresses with pagination", description = "Returns paginated addresses in the system. ADMIN only.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Addresses retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class) // O usar un esquema custom para paginación
                    )
            ),
            @ApiResponse(responseCode = "403", description = "Access denied",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                {
                  "message": "Access denied",
                  "status": 403,
                  "errorCode": "FORBIDDEN",
                  "path": "/api/addresses",
                  "timestamp": "2025-06-08T16:00:00"
                }
                """
                            )
                    )
            )
    })
    @GetMapping
    public ResponseEntity<Page<AddressResponseDTO>> getAllAddresses(Pageable pageable) {
        Page<AddressResponseDTO> addressesPage = addressService.getAllAddresses(pageable);
        return ResponseEntity.ok(addressesPage);
    }
}