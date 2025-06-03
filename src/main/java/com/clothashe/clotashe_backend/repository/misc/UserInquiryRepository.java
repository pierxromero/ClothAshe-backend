package com.clothashe.clotashe_backend.repository.misc;

import com.clothashe.clotashe_backend.model.entity.user.UserInquiryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface UserInquiryRepository extends JpaRepository<UserInquiryEntity, Long> {

    Page<UserInquiryEntity> findByAnswered(Boolean answered, Pageable pageable);

    Page<UserInquiryEntity> findByUserInquiryId(Long userId, Pageable pageable);

    Page<UserInquiryEntity> findByAnsweredAndUserInquiryId(Boolean answered, Long userId, Pageable pageable);

    List<UserInquiryEntity> findByUserInquiryIdOrderByInquiryDateDesc(Long userId);
}