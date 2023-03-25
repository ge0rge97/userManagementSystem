package com.george.spring.userManagmantSystem.service.impl;

import com.george.spring.userManagmantSystem.domain.UserEntity;
import com.george.spring.userManagmantSystem.exception.UserNotFoundException;
import com.george.spring.userManagmantSystem.repository.UserRepository;
import com.george.spring.userManagmantSystem.service.UserService;
import com.george.spring.userManagmantSystem.web.dto.UserDto;
import com.george.spring.userManagmantSystem.web.dto.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDto getUserById(Long id) throws UserNotFoundException {

        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));
        UserDto userDto = userMapper.toDto(userEntity);
        return userDto;
    }
    @Override
    public Iterable<UserDto> getAllUsers() {

        Iterable<UserEntity> userEntities = userRepository.findAll();
        Iterable<UserDto> userDtos = StreamSupport.stream(userEntities.spliterator(), false)
                .map(userEntity -> userMapper.toDto(userEntity))
                .collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long id) throws UserNotFoundException {

        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        if (userDto.getUsername() != null) {
            userEntity.setUsername(userDto.getUsername());
        }
        if (userDto.getPassword() != null) {
            userEntity.setPassword(userDto.getPassword());
        }

        userRepository.save(userEntity);
        UserDto updatedUser = userMapper.toDto(userEntity);
        return updatedUser;
    }

    @Override
    public void deleteUser(Long id) throws UserNotFoundException {

        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));
        userRepository.deleteById(userEntity.getId());
    }
}
