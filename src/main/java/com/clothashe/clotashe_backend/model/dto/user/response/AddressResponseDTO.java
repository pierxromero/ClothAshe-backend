package com.clothashe.clotashe_backend.model.dto.user.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(name = "AddressResponseDTO", description = "DTO used to return full address information.")
public class AddressResponseDTO {

    @Schema(description = "Unique identifier of the address.", example = "101")
    private Long id;

    @Schema(description = "Street name of the address.", example = "Main St")
    private String street;

    @Schema(description = "Street number or building number.", example = "123A")
    private String number;

    @Schema(description = "City of the address.", example = "Buenos Aires")
    private String city;

    @Schema(description = "Province or state of the address.", example = "Buenos Aires")
    private String province;

    @Schema(description = "Postal code of the address.", example = "C1000")
    private String postalCode;

    @Schema(description = "Country of the address.", example = "Argentina")
    private String country;

    @Schema(description = "Additional address information, if any.", example = "Apartment 3B")
    private String additionalInfo;

    @Schema(description = "ID of the user associated with this address.", example = "15")
    private Long userId;
}