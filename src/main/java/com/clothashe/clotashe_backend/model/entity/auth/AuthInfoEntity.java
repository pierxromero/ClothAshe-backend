package com.clothashe.clotashe_backend.model.entity.auth;

import com.clothashe.clotashe_backend.model.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "tbl_auth_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

public class AuthInfoEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_info_id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled;

    @Column(name = "is_locked", nullable = false)
    private Boolean isLocked;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(
            new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
    );
}
@Override public String getPassword() { return passwordHash; }
@Override public String getUsername() { return email; }
@Override public boolean isAccountNonExpired() { return true; }
@Override public boolean isAccountNonLocked() { return !isLocked; }
@Override public boolean isCredentialsNonExpired() { return true; }
@Override public boolean isEnabled() { return isEnabled; }
}