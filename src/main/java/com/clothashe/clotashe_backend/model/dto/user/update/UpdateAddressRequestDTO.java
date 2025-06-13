package com.clothashe.clotashe_backend.model.dto.user.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(
        name = "UpdateAddressRequestDTO",
        description = "DTO used to update an existing address. All fields are optional but must respect length and format constraints. Empty strings are not allowed except for additionalInfo."
)
public class UpdateAddressRequestDTO {

    @Size(max = 100, message = "Street must be at most 100 characters")
    @Pattern(regexp = "^.+$", message = "Street cannot be empty if provided")
    @Schema(
            description = "Street name of the address.",
            example = "123 Main St",
            maxLength = 100,
            required = false
    )
    private String street;

    @Size(max = 10, message = "Number must be at most 10 characters")
    @Pattern(regexp = "^.+$", message = "Number cannot be empty if provided")
    @Schema(
            description = "Street number of the address.",
            example = "45B",
            maxLength = 10,
            required = false
    )
    private String number;

    @Size(max = 50, message = "City must be at most 50 characters")
    @Pattern(regexp = "^.+$", message = "City cannot be empty if provided")
    @Schema(
            description = "City of the address.",
            example = "New York",
            maxLength = 50,
            required = false
    )
    private String city;

    @Size(max = 50, message = "Province must be at most 50 characters")
    @Pattern(regexp = "^.+$", message = "Province cannot be empty if provided")
    @Schema(
            description = "Province or state of the address.",
            example = "New York",
            maxLength = 50,
            required = false
    )
    private String province;

    @Pattern(regexp = "^[A-Za-z0-9\\- ]{4,10}$", message = "Postal code format is invalid")
    @Schema(
            description = "Postal code of the address. Must match pattern: alphanumeric, dash, or space; length 4-10.",
            example = "12345-678",
            required = false,
            pattern = "^[A-Za-z0-9\\- ]{4,10}$"
    )
    private String postalCode;

    @Size(max = 50, message = "Country must be at most 50 characters")
    @Pattern(regexp = "^.+$", message = "Country cannot be empty if provided")
    @Schema(
            description = "Country of the address.",
            example = "USA",
            maxLength = 50,
            required = false
    )
    private String country;

    @Size(max = 255, message = "Additional info must be at most 255 characters")
    @Schema(
            description = "Additional information for the address, such as apartment or floor number.",
            example = "Apartment 12B",
            maxLength = 255,
            required = false
    )
    private String additionalInfo;
}