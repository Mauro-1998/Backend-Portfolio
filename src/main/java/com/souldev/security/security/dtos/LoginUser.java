package com.souldev.security.security.dtos;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class LoginUser {
    /*
    @NotBlank
    private String userName;
     */
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    
}
