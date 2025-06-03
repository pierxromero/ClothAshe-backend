package com.clothashe.clotashe_backend.model.dto.user.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO to create a new user inquiry.")
public class CreateUserInquiryRequestDTO {

    @NotBlank(message = "The inquiry message must not be blank")
    @Schema(description = "Message of the inquiry", example = "What are the shipping options?")
    private String message;
}
