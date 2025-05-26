package com.clothashe.clotashe_backend.model.entity.admin;


import com.clothashe.clotashe_backend.model.entity.auth.AuthInfoEntity;
import com.clothashe.clotashe_backend.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_admin")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

public class AdminEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_info_id", unique = true, nullable = false)
    private AuthInfoEntity authInfo;

    @Column(name = "hire_date", nullable = false)
    private LocalDateTime hireDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "position", nullable = false)
    private Role position;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
}