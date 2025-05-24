package com.clothashe.clotashe_backend.model.entity.product;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_size")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SizeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "size_id")
    private Long id;

    @Column(name = "size_code", nullable = false, unique = true)
    private String code; // Ej: S, M, L, XL

    @Column(name = "size_description")
    private String description;
}
