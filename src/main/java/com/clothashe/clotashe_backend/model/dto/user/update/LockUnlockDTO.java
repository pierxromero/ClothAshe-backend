package com.clothashe.clotashe_backend.model.dto.user.update;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LockUnlockDTO {
    @NotNull
    private Boolean lock;
}