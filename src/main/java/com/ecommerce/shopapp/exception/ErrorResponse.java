package com.ecommerce.shopapp.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Admin
 * @created 9/16/2024
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private Date timestamp;
    private int status;
    private String path;
    private String message;
    private String error;

}
