package com.clothashe.clotashe_backend.mapper.auth;

import com.clothashe.clotashe_backend.mapper.misc.AddressMapper;
import com.clothashe.clotashe_backend.mapper.misc.FavoriteProductMapper;
import com.clothashe.clotashe_backend.mapper.order.CartMapper;
import com.clothashe.clotashe_backend.mapper.order.OrderMapper;
import com.clothashe.clotashe_backend.model.dto.user.create.CreateUserRequestDTO;
import com.clothashe.clotashe_backend.model.dto.user.response.*;
import com.clothashe.clotashe_backend.model.entity.user.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface UserMapper {

    UserEntity toEntity(CreateUserRequestDTO dto);

    UserResponseDTO toDto(UserEntity entity);

    UserDTO toUserDTO(UserEntity entity);

    UserFavoritesDTO toUserFavoritesDTO(UserEntity entity);

    UserAddressesDTO toUserAddressesDTO(UserEntity entity);

    UserOrderHistoryDTO toUserOrderHistoryDTO(UserEntity entity);
}