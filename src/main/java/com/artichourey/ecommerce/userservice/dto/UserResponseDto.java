package com.artichourey.ecommerce.userservice.dto;

import java.time.Instant;

import com.artichourey.ecommerce.userservice.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {

	private Long id;
	private String name;
	private String email;
	private String phone;
	
	private Role role;
	private Instant createdAt;
	private Instant updatedAt;
	
	
	
}
