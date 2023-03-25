package com.george.spring.userManagmantSystem.service;

import com.george.spring.userManagmantSystem.exception.UserAlreadyExistsException;
import com.george.spring.userManagmantSystem.web.dto.UserDto;

public interface AuthService {

    UserDto login(UserDto userDto);
    UserDto register(UserDto userDto) throws UserAlreadyExistsException;
}
