package com.artichourey.ecommerce.userservice.mapper;


import org.springframework.stereotype.Component;

import com.artichourey.ecommerce.userservice.dto.LoginResponseDto;
import com.artichourey.ecommerce.userservice.entity.User;
@Component
public class AuthMapper {

	public LoginResponseDto toLoginResponse(User user, String token) {
		
		return new LoginResponseDto(token,"Bearer",user.getId(),user.getEmail(),user.getName());
		
	}
}
