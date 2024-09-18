package com.ecommerce.shopapp.services;

import com.ecommerce.shopapp.dtos.request.Login;
import com.ecommerce.shopapp.dtos.request.UserDTO;
import com.ecommerce.shopapp.dtos.response.TokenResponse;
import com.ecommerce.shopapp.entity.User;
import com.ecommerce.shopapp.exception.DataNotFoundException;

public interface IUserService {




    User createUser(UserDTO userDTO) throws DataNotFoundException;

    String login(String phoneNumber, String password) throws Exception;


}
