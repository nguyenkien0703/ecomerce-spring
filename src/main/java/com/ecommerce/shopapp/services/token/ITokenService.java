package com.ecommerce.shopapp.services.token;

import com.ecommerce.shopapp.entity.Token;
import com.ecommerce.shopapp.entity.User;

public interface ITokenService {
    Token addToken(User user, String token, boolean isMobileDevice);

    Token refreshToken(String refreshToken, User user ) throws Exception;
}
