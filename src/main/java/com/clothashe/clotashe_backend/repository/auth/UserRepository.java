package com.clothashe.clotashe_backend.repository.auth;

import com.clothashe.clotashe_backend.model.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByEmail(String email);
    boolean existsByNumberPhone(String numberPhone);
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByNumberPhoneAndIdNot(String numberPhone, Long id);
}