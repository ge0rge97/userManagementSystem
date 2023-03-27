package com.george.spring.userManagmantSystem.web.security;

import com.george.spring.userManagmantSystem.domain.UserEntity;
import com.george.spring.userManagmantSystem.exception.UserNotFoundException;
import com.george.spring.userManagmantSystem.service.UserService;
import com.george.spring.userManagmantSystem.service.props.JwtProperties;
import com.george.spring.userManagmantSystem.web.dto.UserDto;
import com.george.spring.userManagmantSystem.web.dto.auth.JwtResponse;
import com.george.spring.userManagmantSystem.web.dto.mapper.UserMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final UserMapper userMapper;
    private Key key;
//    @Autowired
//    public JwtTokenProvider(JwtProperties jwtProperties,
//                            UserDetailsService userDetailsService,
//                            UserService userService) {
//        this.jwtProperties = jwtProperties;
//        this.userDetailsService = userDetailsService;
//        this.userService = userService;
//    }
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String createAccessToken(Long userId, String username) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("id", userId);
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getAccess());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key)
                .compact();
    }
    public String createRefreshToken(Long userId, String username) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("id", userId);
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getRefresh());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key)
                .compact();
    }

    public JwtResponse refreshUserTokens(String refreshToken) throws UserNotFoundException {
        JwtResponse jwtResponse = new JwtResponse();
        if (!validateToken(refreshToken)) {
            throw new AccessDeniedException("Access Denied");
        }
        Long userId = Long.valueOf(getId(refreshToken));
        UserDto userDto = userService.getUserById(userId);
        UserEntity user = userMapper.toEntity(userDto);
        jwtResponse.setId(userId);
        jwtResponse.setUsername(user.getUsername());
        jwtResponse.setAccessToken(createAccessToken(userId, user.getUsername()));
        jwtResponse.setRefreshToken(createRefreshToken(userId, user.getUsername()));
        return jwtResponse;
    }

    public boolean validateToken(String token) {
        Jws<Claims> claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
        return !claims.getBody().getExpiration().before(new Date());
    }

    private String getId(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("id")
                .toString();
    }

    private String getUsername(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Authentication getAuthentication(String token) {
        String username = getUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}