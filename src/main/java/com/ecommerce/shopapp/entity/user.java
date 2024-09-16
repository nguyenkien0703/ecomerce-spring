package com.ecommerce.shopapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "users")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class user {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "fullname", length = 100)
    private String fullName;

    @Column(name = "phone_number", length = 10, nullable = true)
    private String phoneNumber;

    // ALTER TABLE users ADD COLUMN email VARCHAR(255) DEFAULT '';
    @Column(name = "email", length = 255, nullable = true)
    private String email;

    @Column(name = "address", length = 200)
    private String address;


    @Column(name = "password", length = 200, nullable = false)
    private String password;
}
