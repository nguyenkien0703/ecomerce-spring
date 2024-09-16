package com.ecommerce.shopapp.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author Admin
 * @created 9/16/2024
 */
@Getter
@Setter
public class Login implements Serializable {
    private static final long serialVersionUID = UUID.randomUUID().getMostSignificantBits();
    private String email;
    private String password;
}
