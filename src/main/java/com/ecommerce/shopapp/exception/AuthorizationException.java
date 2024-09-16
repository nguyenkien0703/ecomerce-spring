package com.ecommerce.shopapp.exception;

/**
 * @author Admin
 * @created 9/16/2024
 */
public class AuthorizationException extends RuntimeException{

    public AuthorizationException(String message) {
        super(message);
    }

}
