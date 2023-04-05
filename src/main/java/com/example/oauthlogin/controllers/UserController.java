package com.example.oauthlogin.controllers;

import com.example.oauthlogin.dto.UserDto;
import com.example.oauthlogin.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public UserDto create(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }


}
