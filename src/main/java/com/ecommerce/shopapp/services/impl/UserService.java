package com.ecommerce.shopapp.services.impl;

import com.ecommerce.shopapp.components.JwtTokenUtil;
import com.ecommerce.shopapp.dtos.request.UserDTO;
import com.ecommerce.shopapp.entity.Role;
import com.ecommerce.shopapp.entity.User;
import com.ecommerce.shopapp.exception.DataNotFoundException;
import com.ecommerce.shopapp.repositories.RoleRepository;
import com.ecommerce.shopapp.repositories.UserRepository;
import com.ecommerce.shopapp.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Admin
 * @created 9/16/2024
 */
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(UserDTO userDTO) throws DataNotFoundException {
        String phoneNumber = userDTO.getPhoneNumber();
        if(userRepository.existsByPhoneNumber(phoneNumber)){
            throw new DataIntegrityViolationException("phone number already exists");
        }
        //convert from userDTO => user
        User newUser = User.builder()
                .fullName(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .address(userDTO.getAddress())
                .dateOfBirth(userDTO.getDateOfBirth())
                .facebookAccountId(userDTO.getFacebookAccountId())
                .googleAccountId(userDTO.getGoogleAccountId())
                .active(true)
                .build();


        Role role =roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException(
                        "role not found"));
        newUser.setRole(role);

        // kiem tra new co accountId, ko yeu cau password
        if(userDTO.getFacebookAccountId().equals("0") && userDTO.getGoogleAccountId().equals("0")) {
            String password = userDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            newUser.setPassword(encodedPassword);
        }
        return userRepository.save(newUser);



    }

    @Override
    public String login(String phoneNumber, String password)  throws Exception{
        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
        if(optionalUser.isEmpty()){
            throw new DataNotFoundException("invalid phone number or password");
        }
        User existingUser = optionalUser.get();
        // check password
        if(existingUser.getFacebookAccountId().equals("0") && existingUser.getGoogleAccountId().equals("0")){
            if(!passwordEncoder.matches(password, existingUser.getPassword())){
                throw new BadCredentialsException("invalid password");
            }
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                phoneNumber, password
        );

        //authenticate with spring  security
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }


}
