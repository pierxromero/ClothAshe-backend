package com.clothashe.clotashe_backend.model.dto.user.response;

import com.clothashe.clotashe_backend.model.dto.product.response.ProductResponseDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFavoritesDTO {
    private Long userId;
    private List<FavoriteProductResponseDTO> favorites;
}
