package com.ecommerce.shopapp.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data//toString, show detail info or object
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO  extends SocialAccountDTO{

    @JsonProperty("fullname")
    private String fullName;

    @JsonProperty("phone_number")
    private String phoneNumber = "";

    @JsonProperty("email")
    private String email = "";

    private String address = "";

    @NotBlank(message = "Password cannot be blank")
    private String password = "";

    @JsonProperty("retype_password")
    private String retypePassword = "";

    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    @JsonProperty("facebook_account_id")
    private String facebookAccountId;

    @JsonProperty("google_account_id")
    private String googleAccountId;

    @NotNull(message = "Role ID is required")
    @JsonProperty("role_id")
    //role admin not permitted
    private Long roleId;
}
