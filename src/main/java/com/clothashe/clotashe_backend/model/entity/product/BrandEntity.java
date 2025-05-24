package com.clothashe.clotashe_backend.model.entity.product;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_brand")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrandEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long id;

    @Column(name = "brand_name", nullable = false, unique = true)
    private String name;

    @Column(name = "brand_description", columnDefinition = "TEXT")
    private String description;
}