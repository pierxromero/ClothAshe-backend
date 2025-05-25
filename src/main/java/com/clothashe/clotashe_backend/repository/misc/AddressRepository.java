package com.clothashe.clotashe_backend.repository.misc;

import com.clothashe.clotashe_backend.model.entity.user.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
}