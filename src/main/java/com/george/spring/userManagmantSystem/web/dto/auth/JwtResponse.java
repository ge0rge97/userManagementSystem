package com.george.spring.userManagmantSystem.web.dto.auth;

public class JwtResponse {

	private Long id;
	private String username;
	private String accessToken;
	private String refreshToken;
	//Empty Constructor
	public JwtResponse() { }
	//Getters & Setters
	public Long getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
