package com.clothashe.clotashe_backend.model.dto.user.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO to respond to an existing inquiry.")
public class AnswerInquiryRequestDTO {

    @NotNull(message = "The inquiry ID must be provided")
    @Schema(description = "ID of the inquiry to answer", example = "10")
    private Long inquiryId;

    @NotBlank(message = "The answer must not be blank")
    @Schema(description = "Answer to the user inquiry", example = "We offer standard and express shipping.")
    private String answer;
}