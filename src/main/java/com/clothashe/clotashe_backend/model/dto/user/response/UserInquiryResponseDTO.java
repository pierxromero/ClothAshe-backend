package com.clothashe.clotashe_backend.model.dto.user.response;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(description = "Data Transfer Object representing a user's inquiry or question submitted to the administration.")
public class UserInquiryResponseDTO {

    @Schema(description = "Unique identifier of the inquiry.", example = "101")
    private Long id;

    @Schema(description = "Message content of the inquiry sent by the user.", example = "I have a question about my recent order.")
    @NotBlank(message = "The inquiry message must not be blank")
    private String message;

    @Schema(description = "Date and time when the inquiry was submitted.", example = "2025-05-25T14:30:00")
    @NotNull(message = "The inquiry date must not be null")
    @PastOrPresent(message = "The inquiry date cannot be in the future")
    private LocalDateTime inquiryDate;

    @Schema(description = "Flag indicating if the inquiry has been answered.", example = "false")
    @NotNull(message = "Answered flag must not be null")
    private Boolean answered;

    @Schema(description = "Response message provided by the admin, if answered.", example = "Your order will be shipped tomorrow.", maxLength = 1000)
    @Size(max = 1000)
    private String answer;

    @Schema(description = "Date and time when the answer was given.", example = "2025-05-26T09:00:00")
    @PastOrPresent(message = "The answer date cannot be in the future")
    private LocalDateTime answerDate;

    @Schema(description = "User who submitted the inquiry.", required = true)
    @NotNull(message = "User information must not be null")
    @Valid
    private UserDTO userInquiry;

    @Schema(description = "User who answered the inquiry, if applicable.")
    @Valid
    private UserDTO answeredBy;
}