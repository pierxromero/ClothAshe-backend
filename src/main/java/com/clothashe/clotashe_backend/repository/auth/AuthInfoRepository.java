package com.clothashe.clotashe_backend.repository.auth;

import com.clothashe.clotashe_backend.model.entity.auth.AuthInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthInfoRepository extends JpaRepository<AuthInfoEntity, Long> {
}