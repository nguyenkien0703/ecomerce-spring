package com.ecommerce.shopapp.service;

import com.ecommerce.shopapp.dto.request.Login;
import com.ecommerce.shopapp.dto.response.TokenResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService{

    TokenResponse loginUser(Login login);

    TokenResponse register(Login login);

}
