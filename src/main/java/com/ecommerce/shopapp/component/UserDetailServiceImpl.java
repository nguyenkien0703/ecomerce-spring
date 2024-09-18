package com.ecommerce.shopapp.component;

import com.ecommerce.shopapp.entity.User;
import com.ecommerce.shopapp.exception.ResourceNotFoundException;
import com.ecommerce.shopapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
//
///**
// * @author Admin
// * @created 9/16/2024
// */
//@Service
//@RequiredArgsConstructor
//public class UserDetailServiceImpl implements UserDetailsService {
//    private final UserRepository userRepository;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(username)
//                .orElseThrow(() -> new ResourceNotFoundException("Not found user"));
//        org.springframework.security.core.userdetails.User
//                authUser = new org.springframework.security.core.userdetails.User(
//                user.getEmail(),
//                user.getPassword(),
//                List.of(new SimpleGrantedAuthority(user.getRole()));
//        );
//        return authUser;
//    }
//}
