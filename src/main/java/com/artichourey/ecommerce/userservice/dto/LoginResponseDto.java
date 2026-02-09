package com.artichourey.ecommerce.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginResponseDto {
	
	private String token;
	private String tokenType="Bearer";
	private Long userId;
	private String email;
	private String name;

}
