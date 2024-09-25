package com.ecommerce.shopapp.controllers;

import com.ecommerce.shopapp.dtos.request.RefreshTokenDTO;
import com.ecommerce.shopapp.dtos.request.UserDTO;
import com.ecommerce.shopapp.dtos.request.UserLoginDTO;
import com.ecommerce.shopapp.entity.Token;
import com.ecommerce.shopapp.entity.User;
import com.ecommerce.shopapp.responses.LoginResponse;
import com.ecommerce.shopapp.responses.ResponseObject;
import com.ecommerce.shopapp.responses.UserResponse;
import com.ecommerce.shopapp.services.token.TokenService;
import com.ecommerce.shopapp.services.user.UserService;
import com.ecommerce.shopapp.utils.LocalizationUtils;
import com.ecommerce.shopapp.utils.MessageKeys;
import com.ecommerce.shopapp.utils.ValidationUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;
    private final LocalizationUtils localizationUtils;
    private final TokenService tokenService;

    @PostMapping("/register")
    //can we register an "admin" user ?
    public ResponseEntity<ResponseObject> createUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            List<String> errorMessage = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(ResponseObject.builder().status(HttpStatus.BAD_REQUEST).data(null).message(errorMessage.toString()).

                    build());
        }
        if (userDTO.getEmail() == null || userDTO.getEmail().trim().isBlank()) {
            if (userDTO.getPhoneNumber() == null || userDTO.getPhoneNumber().trim().isBlank()) {
                return ResponseEntity.badRequest().body(ResponseObject.builder().status(HttpStatus.BAD_REQUEST).data(null).message("At least email or phone number is required")

                        .build());
            } else {
                // phone number not blank
                if (!ValidationUtils.isValidPhoneNumber(userDTO.getPhoneNumber())) {
                    throw new Exception("Invalid phone number");
                }
            }

        } else {
            //Email not blank
            if (!ValidationUtils.isValidEmail(userDTO.getEmail())) {
                throw new Exception("Invalid email format");
            }
        }
        if (!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
            return ResponseEntity.badRequest().body(ResponseObject.builder().status(HttpStatus.BAD_REQUEST).data(null).message(localizationUtils.getLocalizedMessage(MessageKeys.PASSWORD_NOT_MATCH)).build());
        }
        User user = userService.createUser(userDTO);
        return ResponseEntity.ok(ResponseObject.builder().status(HttpStatus.CREATED).data(UserResponse.fromUser(user)).message("Account registration successful").build());
    }


    @PostMapping("/login")
    public ResponseEntity<ResponseObject> loginUser(@Valid @RequestBody UserLoginDTO userLoginDTO, HttpServletRequest request) throws Exception {
        // Kiểm tra thông tin đăng nhập và sinh token
        String token = userService.login(userLoginDTO);
        String userAgent = request.getHeader("User-Agent");
        User userDetail = userService.getUserDetailsFromToken(token);
        Token jwtToken = tokenService.addToken(userDetail, token, isMobileDevice(userAgent));

        LoginResponse loginResponse = LoginResponse.builder()
                .message(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_SUCCESSFULLY))
                .token(jwtToken.getToken())
                .tokenType(jwtToken.getTokenType())
                .refreshToken(jwtToken.getRefreshToken())
                .username(userDetail.getUsername())
                .roles(userDetail.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .id(userDetail.getId())
                .build();

        return ResponseEntity.ok().body(ResponseObject.builder().message("Login successfully").data(loginResponse).status(HttpStatus.OK).build());

    }

    @PostMapping("/refreshToken")
    public ResponseEntity<ResponseObject> refreshToken(
            @Valid @RequestBody RefreshTokenDTO refreshTokenDTO
    )throws Exception {
        User userDetail = userService.getUserDetailsFromRefreshToken(refreshTokenDTO.getRefreshToken());
        Token jwtToken= tokenService.refreshToken(refreshTokenDTO.getRefreshToken(), userDetail);
        LoginResponse loginResponse=LoginResponse.builder()
                .message("Refresh token successfully")
                .token(jwtToken.getToken())
                .tokenType(jwtToken.getTokenType())
                .refreshToken(jwtToken.getRefreshToken())
                .username(userDetail.getUsername())
                .roles(userDetail.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .id(userDetail.getId()).build();
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .data(loginResponse)
                        .message(loginResponse.getMessage())
                        .status(HttpStatus.OK)
                        .build());
    }




    private boolean isMobileDevice(String userAgent) {
        // Kiểm tra User-Agent header để xác định thiết bị di động
        // Ví dụ đơn giản:
        return userAgent.toLowerCase().contains("mobile");
    }

    @PostMapping("/details")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<ResponseObject> getUserDetails(
            @RequestHeader("Authorization") String authorizationHeader
    ) throws Exception {
        String extractedToken = authorizationHeader.substring(7);
        User user = userService.getUserDetailsFromToken(extractedToken);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .message("Get user's detail successfully")
                        .data(UserResponse.fromUser(user))
                        .status(HttpStatus.OK)
                        .build()
        );

    }





}
