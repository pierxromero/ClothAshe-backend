package com.clothashe.clotashe_backend.model.dto.user.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAddressesDTO {
    private Long userId;
    private List<AddressResponseDTO> addresses;
}
