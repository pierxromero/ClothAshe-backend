package com.clothashe.clotashe_backend.model.entity.user;

import com.clothashe.clotashe_backend.model.entity.admin.AdminEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_user_inquiry")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserInquiryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_id")
    private Long id;

    @Column(name = "inquiry_message", nullable = false, columnDefinition = "TEXT")
    private String message;

    @CreationTimestamp
    @Column(name = "inquiry_date", nullable = false, updatable = false)
    private LocalDateTime inquiryDate;

    @Column(name = "inquiry_answered", nullable = false)
    private Boolean answered = false;

    @Column(name = "inquiry_answer", columnDefinition = "TEXT")
    private String answer;

    @Column(name = "inquiry_answer_date")
    private LocalDateTime answerDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userInquiry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private AdminEntity adminInquiry;
}