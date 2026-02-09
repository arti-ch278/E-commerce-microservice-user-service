package com.artichourey.ecommerce.userservice.service;

import com.artichourey.ecommerce.userservice.dto.LoginRequestDto;
import com.artichourey.ecommerce.userservice.dto.LoginResponseDto;
import com.artichourey.ecommerce.userservice.dto.UserCreateRequestDto;
import com.artichourey.ecommerce.userservice.dto.UserResponseDto;

public interface UserService {
	
	UserResponseDto register(UserCreateRequestDto dto);
	LoginResponseDto login(LoginRequestDto dto);
	UserResponseDto getById(Long id);
	
	

}
