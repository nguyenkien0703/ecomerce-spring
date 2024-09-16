package com.ecommerce.shopapp.service.impl;

import com.ecommerce.shopapp.dto.request.Login;
import com.ecommerce.shopapp.dto.response.TokenResponse;
import com.ecommerce.shopapp.entity.User;
import com.ecommerce.shopapp.exception.ResourceNotFoundException;
import com.ecommerce.shopapp.repository.UserRepository;
import com.ecommerce.shopapp.service.JwtService;
import com.ecommerce.shopapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Admin
 * @created 9/16/2024
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public TokenResponse loginUser(Login login) {
        User user = userRepository.findByEmail(login.getEmail())
                .orElseThrow(()-> new ResourceNotFoundException("Not foudn user"));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getEmail(),
                        login.getPassword()
                )
        );
        return TokenResponse.builder().accessToken(jwtService.generateToken(login.getEmail())).build();
    }

    @Override
    public TokenResponse register(Login login) {
        User user = User
                .builder()
                .email(login.getEmail())
                .password(passwordEncoder.encode(login.getPassword()))
                .build();
        userRepository.save(user);
        String token = jwtService.generateToken(user.getEmail());
        return TokenResponse.builder().accessToken(token).build();
    }

}
