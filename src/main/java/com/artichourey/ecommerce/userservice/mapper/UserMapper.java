package com.artichourey.ecommerce.userservice.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.artichourey.ecommerce.userservice.dto.UserCreateRequestDto;
import com.artichourey.ecommerce.userservice.dto.UserResponseDto;

import com.artichourey.ecommerce.userservice.entity.User;

@Component
public class UserMapper {
	
	private PasswordEncoder passwordEncoder;
	
	public UserMapper(PasswordEncoder passwordEncoder) {
		this.passwordEncoder=passwordEncoder;
	}
	public User toEntity(UserCreateRequestDto dto) {
		return User.builder().name(dto.getName()).email(dto.getEmail()).
		password(passwordEncoder.encode(dto.getPassword())).phone(dto.getPhone()).roles("USER_ROLE").build();
	}
	
	
	public UserResponseDto toDto(User user) {
	    return UserResponseDto.builder()
	            .id(user.getId())
	            .name(user.getName())
	            .email(user.getEmail())
	            .phone(user.getPhone())
	            .roles(user.getRoles())
	            .createdAt(user.getCreatedAt())
	            .updatedAt(user.getUpdatedAt())
	            .build();
	}
}
