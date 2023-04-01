package com.george.spring.userManagmantSystem.web.controller;

import com.george.spring.userManagmantSystem.exception.UserAlreadyExistsException;
import com.george.spring.userManagmantSystem.service.AuthService;
import com.george.spring.userManagmantSystem.web.dto.UserDto;
import com.george.spring.userManagmantSystem.web.dto.auth.JwtRequest;
import com.george.spring.userManagmantSystem.web.dto.auth.JwtResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name="Auth Controller", description="AuthAPI")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register new user")
    public ResponseEntity registerNewUser(@RequestBody UserDto userDto) {
        try {
            return ResponseEntity.ok(authService.register(userDto));
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/login")
    @Operation(summary = "Login user")
    public JwtResponse login(@RequestBody JwtRequest loginRequest) { return authService.login(loginRequest); }
}
