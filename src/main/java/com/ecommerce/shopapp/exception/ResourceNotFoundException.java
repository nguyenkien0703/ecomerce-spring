package com.ecommerce.shopapp.exception;

/**
 * @author Admin
 * @created 9/16/2024
 */
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
