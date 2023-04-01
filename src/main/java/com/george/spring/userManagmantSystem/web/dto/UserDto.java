package com.george.spring.userManagmantSystem.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "User DTO")
public class UserDto {

    @Schema(description = "User Id", example = "1")
    private Long id;
    @Schema(description = "User Email", example = "test1@test.com")
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(description = "User Password", example = "$2a$10$Xl0yhvzLIaJCDdKBS0Lld.ksK7c2Zytg/ZKFdtIYYQUv8rUfvCR4W")
    private String password;
}
