package com.george.spring.userManagmantSystem.service;

import com.george.spring.userManagmantSystem.exception.UserAlreadyExistsException;
import com.george.spring.userManagmantSystem.web.dto.UserDto;
import com.george.spring.userManagmantSystem.web.dto.auth.JwtRequest;
import com.george.spring.userManagmantSystem.web.dto.auth.JwtResponse;

public interface AuthService {

    JwtResponse login(JwtRequest loginRequest);
    UserDto register(UserDto userDto) throws UserAlreadyExistsException;
}
