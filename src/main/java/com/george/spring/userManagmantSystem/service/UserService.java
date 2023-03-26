package com.george.spring.userManagmantSystem.service;

import com.george.spring.userManagmantSystem.domain.UserEntity;
import com.george.spring.userManagmantSystem.exception.UserNotFoundException;
import com.george.spring.userManagmantSystem.web.dto.UserDto;

public interface UserService {

    UserEntity getByUsername(String username);
    UserDto getUserById(Long id) throws UserNotFoundException;
    Iterable<UserDto> getAllUsers();
    UserDto updateUser(UserDto userDto, Long id) throws UserNotFoundException;
    void deleteUser(Long id) throws UserNotFoundException;
}
