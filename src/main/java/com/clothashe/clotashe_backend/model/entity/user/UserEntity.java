package com.clothashe.clotashe_backend.model.entity.user;

import com.clothashe.clotashe_backend.model.entity.auth.AuthInfoEntity;
import com.clothashe.clotashe_backend.model.entity.cart.CartEntity;
import com.clothashe.clotashe_backend.model.entity.order.OrderEntity;
import com.clothashe.clotashe_backend.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "tbl_users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_full_name")
    private String fullName;

    @Column(name = "user_email", nullable = false, unique = true)
    private String email;

    @Column(name = "user_phone", nullable = false, unique = true)
    private String numberPhone;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private AuthInfoEntity authInfo;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderEntity> purchaseHistory;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FavoriteProductEntity> favorites;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AddressEntity> addresses;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private CartEntity cart;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;
}
