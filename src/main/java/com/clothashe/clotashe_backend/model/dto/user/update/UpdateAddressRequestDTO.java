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
@Schema(name = "UpdateAddressRequestDTO", description = "DTO used to update an existing address.")
public class UpdateAddressRequestDTO {

    @Size(max = 100, message = "Street must be at most 100 characters")
    private String street;

    @Size(max = 10, message = "Number must be at most 10 characters")
    private String number;

    @Size(max = 50, message = "City must be at most 50 characters")
    private String city;

    @Size(max = 50, message = "Province must be at most 50 characters")
    private String province;

    @Pattern(regexp = "^[A-Za-z0-9\\- ]{4,10}$", message = "Postal code format is invalid")
    private String postalCode;

    @Size(max = 50, message = "Country must be at most 50 characters")
    private String country;

    @Size(max = 255, message = "Additional info must be at most 255 characters")
    private String additionalInfo;

}