package com.clothashe.clotashe_backend.repository.auth;

import com.clothashe.clotashe_backend.model.entity.admin.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
}