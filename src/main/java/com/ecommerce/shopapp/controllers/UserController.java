package com.ecommerce.shopapp.controllers;

import com.ecommerce.shopapp.dtos.request.UserDTO;
import com.ecommerce.shopapp.dtos.request.UserLoginDTO;
import com.ecommerce.shopapp.entity.User;
import com.ecommerce.shopapp.responses.ResponseObject;
import com.ecommerce.shopapp.responses.UserResponse;
import com.ecommerce.shopapp.services.IUserService;
import com.ecommerce.shopapp.utils.ValidationUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;
    @PostMapping("/register")
    //can we register an "admin" user ?
    public ResponseEntity<ResponseObject> createUser(@Valid @RequestBody UserDTO userDTO,
                                                     BindingResult bindingResult) throws Exception {
            if(bindingResult.hasErrors()) {
                List<String> errorMessage =  bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(ResponseObject.builder()
                                .status(HttpStatus.BAD_REQUEST)
                                        .data(null)
                                                .message(errorMessage.toString()).

                        build());
            }
            if(userDTO.getEmail() == null || userDTO.getEmail().trim().isBlank()) {
                if(userDTO.getPhoneNumber() == null || userDTO.getPhoneNumber().trim().isBlank()) {
                    return ResponseEntity.badRequest().body(ResponseObject.builder()
                                    .status(HttpStatus.BAD_REQUEST)
                                    .data(null)
                                    .message("At least email or phone number is required")

                            .build());
                }else {
                    // phone number not blank
                    if(!ValidationUtils.isValidPhoneNumber(userDTO.getPhoneNumber())) {
                        throw new Exception("Invalid phone number");
                    }
                }

            }else {
                //Email not blank
                if (!ValidationUtils.isValidEmail(userDTO.getEmail())) {
                    throw new Exception("Invalid email format");
                }
            }
            if(!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
                return ResponseEntity.badRequest().body(ResponseObject.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .data(null)
                        .message("password not match")
                        .build());
            }
            User user = userService.createUser(userDTO);
            return ResponseEntity.ok(ResponseObject.builder()
                    .status(HttpStatus.CREATED)
                    .data(UserResponse.fromUser(user))
                    .message("Account registration successful")
                    .build());
    }








    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        try{
            return ResponseEntity.ok("login user success");
        }catch (Exception e ) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


}
