package com.george.spring.userManagmantSystem.web.security;

import com.george.spring.userManagmantSystem.domain.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class JwtEntityFactory {

    public  static JwtEntity create(UserEntity user) {
        return new JwtEntity(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                mapToGrantedAuthority(Collections.singletonList("ROLE_USER"))
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthority(List<String> roles) {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
