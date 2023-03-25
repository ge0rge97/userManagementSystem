package com.george.spring.userManagmantSystem.service.impl;

import com.george.spring.userManagmantSystem.domain.UserEntity;
import com.george.spring.userManagmantSystem.exception.UserAlreadyExistsException;
import com.george.spring.userManagmantSystem.repository.UserRepository;
import com.george.spring.userManagmantSystem.service.AuthService;
import com.george.spring.userManagmantSystem.web.dto.UserDto;
import com.george.spring.userManagmantSystem.web.dto.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDto login(UserDto userDto) {
        return null;
    }
    @Override
    public UserDto register(UserDto userDto) throws UserAlreadyExistsException {
        UserEntity userEntity = userMapper.toEntity(userDto);

        if (userRepository.findByUsername(userEntity.getUsername()) != null) {
            throw new UserAlreadyExistsException("User Already Exists");
        }

        userRepository.save(userEntity);
        UserDto createdUser = userMapper.toDto(userEntity);
        return createdUser;
    }
}
