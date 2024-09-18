package com.ecommerce.shopapp.dtos.response;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author Admin
 * @created 9/16/2024
 */
@Getter
@Builder
public class TokenResponse implements Serializable {
    private String accessToken;
}
