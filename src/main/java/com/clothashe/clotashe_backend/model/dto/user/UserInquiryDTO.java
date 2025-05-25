package com.clothashe.clotashe_backend.model.dto.user;


import com.clothashe.clotashe_backend.model.dto.admin.AdminDTO;
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
public class UserInquiryDTO {

    private Long id;

    @NotBlank(message = "The inquiry message must not be blank")
    private String message;

    @NotNull(message = "The inquiry date must not be null")
    @PastOrPresent(message = "The inquiry date cannot be in the future")
    private LocalDateTime inquiryDate;

    @NotNull(message = "Answered flag must not be null")
    private Boolean answered;

    @Size(max = 1000)
    private String answer;

    @PastOrPresent(message = "The answer date cannot be in the future")
    private LocalDateTime answerDate;

    @NotNull(message = "User information must not be null")
    @Valid
    private UserDTO userId;

    @Valid
    private AdminDTO adminId;
}