package com.george.spring.userManagmantSystem.service.impl;

import com.george.spring.userManagmantSystem.domain.UserEntity;
import com.george.spring.userManagmantSystem.exception.UserAlreadyExistsException;
import com.george.spring.userManagmantSystem.repository.UserRepository;
import com.george.spring.userManagmantSystem.service.AuthService;
import com.george.spring.userManagmantSystem.web.dto.UserDto;
import com.george.spring.userManagmantSystem.web.dto.auth.JwtRequest;
import com.george.spring.userManagmantSystem.web.dto.auth.JwtResponse;
import com.george.spring.userManagmantSystem.web.dto.mapper.UserMapper;
import com.george.spring.userManagmantSystem.web.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authentificationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public JwtResponse login(JwtRequest loginRequest) {
        JwtResponse jwtResponse = new JwtResponse();
        authentificationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        UserEntity userEntity = userRepository.findByUsername(loginRequest.getUsername());
        jwtResponse.setId(userEntity.getId());
        jwtResponse.setUsername(userEntity.getUsername());
        jwtResponse.setAccessToken(jwtTokenProvider.createAccessToken(userEntity.getId(), userEntity.getUsername()));
        jwtResponse.setRefreshToken(jwtTokenProvider.createRefreshToken(userEntity.getId(), userEntity.getUsername()));
        return jwtResponse;
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
