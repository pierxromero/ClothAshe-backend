package com.clothashe.clotashe_backend.repository.auth;

import com.clothashe.clotashe_backend.model.entity.auth.AuthInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthInfoRepository extends JpaRepository<AuthInfoEntity, Long> {
    Optional<AuthInfoEntity> findByEmail(String email);
    List<AuthInfoEntity> findByIsLockedTrue();
    Optional<AuthInfoEntity> findByUserId(Long userId);
}