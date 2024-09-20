package com.ecommerce.shopapp.services;

import com.ecommerce.shopapp.dtos.request.Login;
import com.ecommerce.shopapp.dtos.request.UpdateUserDTO;
import com.ecommerce.shopapp.dtos.request.UserDTO;
import com.ecommerce.shopapp.dtos.request.UserLoginDTO;
import com.ecommerce.shopapp.dtos.response.TokenResponse;
import com.ecommerce.shopapp.entity.User;
import com.ecommerce.shopapp.exception.DataNotFoundException;
import com.ecommerce.shopapp.exception.InvalidPasswordException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {


    User createUser(UserDTO userDTO) throws Exception;

    String login(UserLoginDTO userLoginDTO) throws Exception;

    User getUserDetailsFromToken(String token) throws Exception;
    User getUserDetailsFromRefreshToken(String refreshToken) throws Exception;
    User updateUser(Long userId, UpdateUserDTO updatedUserDTO) throws Exception;

    Page<User> findAll(String keyword, Pageable pageable) throws Exception;

    void resetPassword(Long userId, String newPassword)    throws InvalidPasswordException, DataNotFoundException;


    void blockOrEnable(Long userId, Boolean active) throws DataNotFoundException;

    void changeProfileImage(Long userId, String imageName) throws Exception;

}
