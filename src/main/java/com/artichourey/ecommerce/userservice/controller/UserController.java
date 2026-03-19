package com.artichourey.ecommerce.userservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;

@Tag(name = "User APIs", description = "Endpoints for user registration, login, and retrieval")
@RestController
@RequestMapping("/api/users")
public class UserController {
	
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService=userService;
	}
	@Operation(
	        summary = "Register a new user",
	        description = "Creates a new user with name, email, phone, password, and role.",
	        responses = {
	            @ApiResponse(responseCode = "201", description = "User created successfully",
	                content = @Content(mediaType = "application/json",
	                    schema = @Schema(implementation = UserResponseDto.class))),
	            @ApiResponse(responseCode = "400", description = "Invalid input",
	                content = @Content)
	        }
	    )
	@PostMapping("/register")
	public ResponseEntity<UserResponseDto> register (@Valid @RequestBody UserCreateRequestDto dto){
		UserResponseDto created=userService.register(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(created) ;
		
	}
	@Operation(
	        summary = "Login a user",
	        description = "Validates user credentials and returns JWT token.",
	        responses = {
	            @ApiResponse(responseCode = "200", description = "Login successful",
	                content = @Content(mediaType = "application/json",
	                    schema = @Schema(implementation = LoginResponseDto.class))),
	            @ApiResponse(responseCode = "401", description = "Invalid credentials",
	                content = @Content)
	        }
	    )
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto dto){
		LoginResponseDto resp=userService.login(dto);
		return ResponseEntity.status(HttpStatus.OK).body(resp);
	}
	@Operation(
	        summary = "Get user by ID",
	        description = "Returns user details for a given user ID. JWT authentication required.",
	        responses = {
	            @ApiResponse(responseCode = "200", description = "User found",
	                content = @Content(mediaType = "application/json",
	                    schema = @Schema(implementation = UserResponseDto.class))),
	            @ApiResponse(responseCode = "404", description = "User not found",
	                content = @Content),
	            @ApiResponse(responseCode = "401", description = "Unauthorized",
	                content = @Content)
	        }
	    )
	@GetMapping("/{id}")
	public ResponseEntity<UserResponseDto> getById(@PathVariable Long id, Authentication authentication){
		UserResponseDto dto =userService.getById(id);
		return ResponseEntity.ok(dto);
		
	}

}
