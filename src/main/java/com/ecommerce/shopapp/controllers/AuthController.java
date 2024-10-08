package com.ecommerce.shopapp.controllers;

import com.ecommerce.shopapp.services.user.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Admin
 * @created 9/16/2024
 */
@RestController
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final IUserService userService;
//    @PostMapping("/login")
//    public ResponseEntity<TokenResponse> login(@RequestBody Login login) {
////        log.info();
//        return ResponseEntity.ok(userService.loginUser(login));
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<TokenResponse> register(@RequestBody Login login) {
////        log.info();
//        return ResponseEntity.ok(userService.register(login));
//    }
}
