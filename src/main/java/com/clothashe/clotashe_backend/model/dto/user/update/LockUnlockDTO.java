package com.clothashe.clotashe_backend.model.dto.user.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(
        name = "LockUnlockDTO",
        description = "DTO used to lock or unlock a user account."
)
public class LockUnlockDTO {

    @NotNull
    @Schema(
            description = "Flag indicating whether to lock (true) or unlock (false) the user account.",
            example = "true",
            required = true
    )
    private Boolean lock;
}