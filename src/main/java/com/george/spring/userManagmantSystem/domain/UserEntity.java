package com.george.spring.userManagmantSystem.domain;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;

    public UserEntity() { }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
