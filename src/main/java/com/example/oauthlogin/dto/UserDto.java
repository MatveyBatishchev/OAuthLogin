package com.example.oauthlogin.dto;

import com.example.oauthlogin.model.domain.UserProvider;
import lombok.Data;

@Data
public class UserDto {

    private int id;

    private String email;

    private String password;

    private String name;

    private UserProvider provider;

}

