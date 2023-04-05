package com.example.oauthlogin.services;

import com.example.oauthlogin.dto.UserDto;
import com.example.oauthlogin.errors.UserAlreadyExistsException;
import com.example.oauthlogin.mappers.UserMapper;
import com.example.oauthlogin.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserDto createUser(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserAlreadyExistsException("There is already an account with that email: " + userDto.getEmail());
        }
        return userMapper.toDto(userRepository.save(userMapper.toUser(userDto)));
    }

}
