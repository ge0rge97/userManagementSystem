package com.george.spring.userManagmantSystem.web.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Request for login")
public class JwtRequest {

	@Schema(description = "Email", example = "demo_user")
	private String username;
	@Schema(description = "Email", example = "12345")
	private String password;
}
