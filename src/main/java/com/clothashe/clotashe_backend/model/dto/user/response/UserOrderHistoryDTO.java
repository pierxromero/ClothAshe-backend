package com.clothashe.clotashe_backend.model.dto.user.response;

import com.clothashe.clotashe_backend.model.dto.order.response.OrderResponseDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrderHistoryDTO {
    private Long userId;
    private String fullName;
    private List<OrderResponseDTO> purchaseHistory;
}