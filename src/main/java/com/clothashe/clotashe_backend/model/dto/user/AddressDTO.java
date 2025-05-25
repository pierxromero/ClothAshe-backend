package com.clothashe.clotashe_backend.model.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(name = "AddressDTO", description = "Data Transfer Object representing a user's address.")
public class AddressDTO {
    @Schema(description = "Unique identifier of the address.", example = "101")
    private Long id;

    @Schema(description = "Street name of the address.", example = "Main St", maxLength = 100)
    @NotBlank(message = "Street must not be blank")
    @Size(max = 100, message = "Street must be at most 100 characters")
    private String street;

    @Schema(description = "Street number or building number.", example = "123A", maxLength = 10)
    @NotBlank(message = "Number must not be blank")
    @Size(max = 10, message = "Number must be at most 10 characters")
    private String number;

    @Schema(description = "City of the address.", example = "Buenos Aires", maxLength = 50)
    @NotBlank(message = "City must not be blank")
    @Size(max = 50, message = "City must be at most 50 characters")
    private String city;

    @Schema(description = "Province or state of the address.", example = "Buenos Aires", maxLength = 50)
    @NotBlank(message = "Province must not be blank")
    @Size(max = 50, message = "Province must be at most 50 characters")
    private String province;

    @Schema(description = "Postal code of the address.", example = "C1000", pattern = "^[A-Za-z0-9\\- ]{4,10}$")
    @NotBlank(message = "Postal code must not be blank")
    @Pattern(regexp = "^[A-Za-z0-9\\- ]{4,10}$", message = "Postal code format is invalid")
    private String postalCode;

    @Schema(description = "Country of the address.", example = "Argentina", maxLength = 50)
    @NotBlank(message = "Country must not be blank")
    @Size(max = 50, message = "Country must be at most 50 characters")
    private String country;

    @Schema(description = "Additional address information, if any.", example = "Apartment 3B", maxLength = 255)
    @Size(max = 255, message = "Additional info must be at most 255 characters")
    private String additionalInfo;

    @Schema(description = "ID of the user associated with this address.", example = "15", required = true)
    @NotNull(message = "User ID must not be null")
    private Long userId;
}
