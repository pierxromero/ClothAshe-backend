package com.clothashe.clotashe_backend.model.entity.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @Column(name = "address_street", nullable = false)
    private String street;

    @Column(name = "address_number", nullable = false)
    private String number;

    @Column(name = "address_city", nullable = false)
    private String city;

    @Column(name = "address_province", nullable = false)
    private String province;

    @Column(name = "address_postal_code", nullable = false)
    private String postalCode;

    @Column(name = "address_country", nullable = false)
    private String country;

    @Column(name = "additional_info")
    private String additionalInfo;

   @ManyToOne
   @JoinColumn(name = "user_id", nullable = false)
   private UserEntity user;
}