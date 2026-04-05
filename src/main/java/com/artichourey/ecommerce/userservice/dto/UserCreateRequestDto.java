package com.artichourey.ecommerce.userservice.dto;

import com.artichourey.ecommerce.userservice.enums.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request DTO for user registration")
public class UserCreateRequestDto {
	
	
	@NotBlank(message = "Name is required")
	@Schema(description = "Full name of the user", example = "John Doe", required = true)
    private String name;
	
	@NotBlank(message = "Phone number is required")
	@Pattern(
	    regexp = "^\\+\\d{10,15}$",
	    message = "Invalid phone number. Must be in format +919876543210"
	)
	@Schema(description = "Phone number of the user", example = "+919876543210", required = true)
	private String phone;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Schema(description = "Email address of the user", example = "john@example.com", required = true)
    private String email;

    @NotBlank(message = "Password is required")
    @Schema(description = "Password for the account", example = "Password123!", required = true)
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;
    
    @NotNull(message = "Role is required")
    @Schema(description = "User role , Possible values:ROLE_USER,ROLE_ADMIN ", example = "ROLE_USER", required = true)
    private Role role;             

}
