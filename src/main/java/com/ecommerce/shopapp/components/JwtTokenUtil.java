package com.ecommerce.shopapp.components;

import com.ecommerce.shopapp.entity.Token;
import com.ecommerce.shopapp.entity.User;
import com.ecommerce.shopapp.exception.InvalidParamException;
import com.ecommerce.shopapp.repositories.TokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    @Value("${jwt.expiration}")
    private long expiration; //save to an environment variable


    @Value("${jwt.expiration-refresh-token}")
    private int expirationRefreshToken;

    @Value("${jwt.secretKey}")
    private String secretKey;


    private final TokenRepository tokenRepository;
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    public String generateToken(User user ) throws InvalidParamException {
        // properties => claims
        Map<String, Object> claims = new HashMap<>();

        // add subject indentifier (phone number or email)

        String subject = getSubject(user);
        claims.put("subject", subject);
        // add user'id
        claims.put("userId", user.getId());
        try{
            String token = Jwts.builder()
                    .setClaims(claims) //how to extract claims from this ?
                    .setSubject(subject)
                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
            return token;

        }catch (Exception e){
          //you can "inject" Logger, instead System.out.println
            throw new InvalidParamException("Cannot create jwt token, error: "+e.getMessage());
            //return null;
        }



    }

    private static String getSubject(User user) {
        // determine subject identifier (phone number or email)
        String subject = user.getPhoneNumber();
        if(subject == null || subject.isBlank()) {
            // if phone number is null or blank, use email as subject
            subject = user.getEmail();
        }
        return subject;
    }

    private SecretKey getSignInKey() {
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }


    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)

                .getBody();
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // check expiration

    public boolean isTokenExpired(String token) {
        Date expirationDate = this.extractClaim(token, Claims::getExpiration);

        return expirationDate.before(new Date());
    }

    public String getSubject(String token) {
        return  extractClaim(token, Claims::getSubject);
    }


    public boolean validateToken(String token, User userDetails) {
        try{
            String subject = extractClaim(token, Claims::getSubject);
            // subject is phoneNumber or email
            Token existingToken = tokenRepository.findByToken(token);
            if(existingToken == null  || existingToken.isRevoked() == true || !userDetails.isActive()) {
                return false;
            }
            return (subject.equals(userDetails.getUsername())) && !isTokenExpired(token);
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }






}
