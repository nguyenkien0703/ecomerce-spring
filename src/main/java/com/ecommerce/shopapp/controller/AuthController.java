package com.ecommerce.shopapp.controller;

import com.ecommerce.shopapp.dto.request.Login;
import com.ecommerce.shopapp.dto.response.TokenResponse;
import com.ecommerce.shopapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Admin
 * @created 9/16/2024
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final UserService userService;
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody Login login) {
//        log.info();
        return ResponseEntity.ok(userService.loginUser(login));
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody Login login) {
//        log.info();
        return ResponseEntity.ok(userService.register(login));
    }
}
