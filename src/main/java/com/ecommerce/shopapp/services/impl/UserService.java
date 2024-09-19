package com.ecommerce.shopapp.services.impl;

import com.ecommerce.shopapp.components.JwtTokenUtil;
import com.ecommerce.shopapp.dtos.request.UserDTO;
import com.ecommerce.shopapp.entity.Role;
import com.ecommerce.shopapp.entity.User;
import com.ecommerce.shopapp.exception.DataNotFoundException;
import com.ecommerce.shopapp.exception.PermissionDenyException;
import com.ecommerce.shopapp.repositories.RoleRepository;
import com.ecommerce.shopapp.repositories.UserRepository;
import com.ecommerce.shopapp.services.IUserService;
import com.ecommerce.shopapp.utils.MessageKeys;
import jakarta.transaction.Transactional;
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
    @Transactional
    public User createUser(UserDTO userDTO) throws Exception {
        //register user
        if (!userDTO.getPhoneNumber().isBlank() && userRepository.existsByPhoneNumber(userDTO.getPhoneNumber())) {
            throw new DataIntegrityViolationException("Phone number already exists");
        }
        if (!userDTO.getEmail().isBlank() && userRepository.existsByEmail(userDTO.getEmail())) {
            throw new DataIntegrityViolationException("Email already exists");
        }
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException(
//                        localizationUtils.getLocalizedMessage(
                                MessageKeys.ROLE_DOES_NOT_EXISTS)
//                )
                );


        if (role.getName().equalsIgnoreCase(Role.ADMIN)) {
            throw new PermissionDenyException("Registering admin accounts is not allowed");
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

        newUser.setRole(role);

        if (!userDTO.isSocialLogin()) {
            String password = userDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            newUser.setPassword(encodedPassword);
        }
        return userRepository.save(newUser);
    }


    @Override
    public String login(String phoneNumber, String password) throws Exception {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
        if (optionalUser.isEmpty()) {
            throw new DataNotFoundException("invalid phone number or password");
        }
        User existingUser = optionalUser.get();
        // check password
        if (existingUser.getFacebookAccountId().equals("0") && existingUser.getGoogleAccountId().equals("0")) {
            if (!passwordEncoder.matches(password, existingUser.getPassword())) {
                throw new BadCredentialsException("invalid password");
            }
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                phoneNumber, password,
                existingUser.getAuthorities()
        );

        //authenticate with spring  security
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }


}
