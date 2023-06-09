package com.george.spring.userManagmantSystem.service.impl;

import com.george.spring.userManagmantSystem.domain.UserEntity;
import com.george.spring.userManagmantSystem.exception.UserAlreadyExistsException;
import com.george.spring.userManagmantSystem.repository.UserRepository;
import com.george.spring.userManagmantSystem.service.AuthService;
import com.george.spring.userManagmantSystem.service.UserService;
import com.george.spring.userManagmantSystem.web.dto.UserDto;
import com.george.spring.userManagmantSystem.web.dto.auth.JwtRequest;
import com.george.spring.userManagmantSystem.web.dto.auth.JwtResponse;
import com.george.spring.userManagmantSystem.web.dto.mapper.UserMapper;
import com.george.spring.userManagmantSystem.web.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserService userService;
    private final AuthenticationManager authentificationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public JwtResponse login(JwtRequest loginRequest) {
        JwtResponse jwtResponse = new JwtResponse();
        authentificationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        UserEntity user = userService.getByUsername(loginRequest.getUsername());
        jwtResponse.setId(user.getId());
        jwtResponse.setUsername(user.getUsername());
        jwtResponse.setAccessToken(jwtTokenProvider.createAccessToken(user.getId(), user.getUsername()));
        jwtResponse.setRefreshToken(jwtTokenProvider.createRefreshToken(user.getId(), user.getUsername()));
        return jwtResponse;
    }
    @Override
    public UserDto register(UserDto userDto) throws UserAlreadyExistsException {

        UserEntity user = userMapper.toEntity(userDto);
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistsException("User Already Exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        UserDto createdUser = userMapper.toDto(user);
        return createdUser;
    }
}
