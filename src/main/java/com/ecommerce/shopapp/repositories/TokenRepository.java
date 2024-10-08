package com.ecommerce.shopapp.repositories;

import com.ecommerce.shopapp.entity.Token;
import com.ecommerce.shopapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findByUser(User user);
    Token findByToken(String token);
    Token findByRefreshToken(String token);
}

