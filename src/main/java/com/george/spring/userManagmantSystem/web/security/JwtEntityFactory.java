package com.george.spring.userManagmantSystem.web.security;

import com.george.spring.userManagmantSystem.domain.UserEntity;

public class JwtEntityFactory {

    public static JwtEntity create(UserEntity user) {
        return new JwtEntity(
                user.getId(),
                user.getUsername(),
                user.getPassword()
        );
    }
}
