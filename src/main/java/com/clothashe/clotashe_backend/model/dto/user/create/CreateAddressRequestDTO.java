package com.clothashe.clotashe_backend.model.dto.user.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(name = "CreateAddressRequestDTO", description = "DTO used to create a new address.")
public class CreateAddressRequestDTO {

    @NotBlank(message = "Street must not be blank")
    @Size(max = 100, message = "Street must be at most 100 characters")
    private String street;

    @NotBlank(message = "Number must not be blank")
    @Size(max = 10, message = "Number must be at most 10 characters")
    private String number;

    @NotBlank(message = "City must not be blank")
    @Size(max = 50, message = "City must be at most 50 characters")
    private String city;

    @NotBlank(message = "Province must not be blank")
    @Size(max = 50, message = "Province must be at most 50 characters")
    private String province;

    @NotBlank(message = "Postal code must not be blank")
    @Pattern(regexp = "^[A-Za-z0-9\\- ]{4,10}$", message = "Postal code format is invalid")
    private String postalCode;

    @NotBlank(message = "Country must not be blank")
    @Size(max = 50, message = "Country must be at most 50 characters")
    private String country;

    @Size(max = 255, message = "Additional info must be at most 255 characters")
    private String additionalInfo;

}