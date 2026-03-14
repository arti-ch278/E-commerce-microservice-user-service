package com.artichourey.ecommerce.userservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Request DTO for user login")
public class LoginRequestDto {

	@NotBlank
	@Email
	@Schema(description = "User email", example = "john@example.com", required = true)
	private String email;
	
	@NotBlank
	@Schema(description = "User password", example = "Password123!", required = true)
	private String password;
}
