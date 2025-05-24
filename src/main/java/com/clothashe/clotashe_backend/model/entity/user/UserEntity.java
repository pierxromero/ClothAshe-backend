package com.clothashe.clotashe_backend.model.entity.user;

import com.clothashe.clotashe_backend.model.entity.auth.AuthInfoEntity;
import com.clothashe.clotashe_backend.model.entity.cart.CartEntity;
import com.clothashe.clotashe_backend.model.entity.core.Person;
import com.clothashe.clotashe_backend.model.entity.order.OrderEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "tbl_users")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "user_id")),
        @AttributeOverride(name = "fullName", column = @Column(name = "user_full_name")),
        @AttributeOverride(name = "email", column = @Column(name = "user_email", nullable = false, unique = true)),
        @AttributeOverride(name = "numberPhone", column = @Column(name = "user_phone", nullable = false, unique = true))
})
public class UserEntity extends Person {

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_info_id", unique = true, nullable = false)
    private AuthInfoEntity authInfo;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderEntity> purchaseHistory;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FavoriteProductEntity> favoriteProducts;

    @Column(name = "last_login")
    private LocalDateTime lastLoginDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AddressEntity> addresses;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private CartEntity cart;
}
