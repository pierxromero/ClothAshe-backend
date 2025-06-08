package com.clothashe.clotashe_backend.model.dto.error;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {

    @Schema(description = "Mensaje de error para mostrar al usuario")
    private String message;

    @Schema(description = "Código HTTP del error")
    private int status;

    @Schema(description = "Fecha y hora del error en formato ISO")
    private String timestamp;

    public ApiError(String message, int status) {
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now().toString();
    }

}