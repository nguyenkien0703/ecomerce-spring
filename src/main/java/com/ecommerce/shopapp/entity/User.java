package com.ecommerce.shopapp.entity;

import com.ecommerce.shopapp.component.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Table(name = "users")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity {



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

    @Column(name = "role")
    private String role;

}
