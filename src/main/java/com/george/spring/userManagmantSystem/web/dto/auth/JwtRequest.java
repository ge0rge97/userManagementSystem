package com.george.spring.userManagmantSystem.web.dto.auth;

public class JwtRequest {
	private String username;
	private String password;
	//Empty constructor
	public JwtRequest() { } 
	//Getters & Setters
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
