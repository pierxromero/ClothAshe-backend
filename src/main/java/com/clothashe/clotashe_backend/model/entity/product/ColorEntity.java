package com.clothashe.clotashe_backend.model.entity.product;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_color")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ColorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "color_id")
    private Long id;

    @Column(name = "color_name", nullable = false, unique = true)
    private String name;

    @Column(name = "hex_code", nullable = false, length = 7)
    private String hexCode;
}