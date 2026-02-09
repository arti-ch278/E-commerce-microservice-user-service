package com.artichourey.ecommerce.userservice.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.artichourey.ecommerce.userservice.dto.LoginRequestDto;
import com.artichourey.ecommerce.userservice.dto.LoginResponseDto;
import com.artichourey.ecommerce.userservice.dto.UserCreateRequestDto;
import com.artichourey.ecommerce.userservice.dto.UserResponseDto;
import com.artichourey.ecommerce.userservice.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService=userService;
	}
	@PostMapping("/register")
	public ResponseEntity<UserResponseDto> register (@Valid @RequestBody UserCreateRequestDto dto){
		UserResponseDto created=userService.register(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(created) ;
		
	}
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto dto){
		LoginResponseDto resp=userService.login(dto);
		return ResponseEntity.status(HttpStatus.OK).body(resp);
	}
	@GetMapping("/{id}")
	public ResponseEntity<UserResponseDto> getById(@PathVariable Long id){
		UserResponseDto dto =userService.getById(id);
		return ResponseEntity.ok(dto);
		
	}

}
