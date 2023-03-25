package com.george.spring.userManagmantSystem.web.controller;

import com.george.spring.userManagmantSystem.domain.UserEntity;
import com.george.spring.userManagmantSystem.repository.UserRepository;
import com.george.spring.userManagmantSystem.service.AuthService;
import com.george.spring.userManagmantSystem.service.UserService;
import com.george.spring.userManagmantSystem.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity registerNewUser(@RequestBody UserDto userDto) {
        try {
//            userRepository.save(userEntity);
            return ResponseEntity.ok(authService.register(userDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
