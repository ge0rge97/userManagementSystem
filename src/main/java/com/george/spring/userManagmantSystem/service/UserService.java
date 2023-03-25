package com.george.spring.userManagmantSystem.service;

import com.george.spring.userManagmantSystem.web.dto.UserDto;

public interface UserService {

    UserDto getUserById(Long id);
    Iterable<UserDto> getAllUsers();
    UserDto updateUser(UserDto userDto, Long id);
    void deleteUser(Long id);
}
