package com.clothashe.clotashe_backend.repository.auth;

import com.clothashe.clotashe_backend.model.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

}