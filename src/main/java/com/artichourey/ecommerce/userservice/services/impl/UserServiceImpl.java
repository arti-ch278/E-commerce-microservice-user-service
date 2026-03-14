package com.artichourey.ecommerce.userservice.services.impl;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.artichourey.ecommerce.userservice.dto.LoginRequestDto;
import com.artichourey.ecommerce.userservice.dto.LoginResponseDto;
import com.artichourey.ecommerce.userservice.dto.UserCreateRequestDto;
import com.artichourey.ecommerce.userservice.dto.UserResponseDto;
import com.artichourey.ecommerce.userservice.entity.User;
import com.artichourey.ecommerce.userservice.exception.ResourceAlreadyExistsException;
import com.artichourey.ecommerce.userservice.exception.ResourceNotFoundException;
import com.artichourey.ecommerce.userservice.mapper.AuthMapper;
import com.artichourey.ecommerce.userservice.mapper.UserMapper;
import com.artichourey.ecommerce.userservice.repository.UserRepository;
import com.artichourey.ecommerce.userservice.service.UserService;
import com.artichourey.ecommerce.userservice.util.JwtUtil;


@Service
public class UserServiceImpl implements UserService {
	
	private final Logger log=LoggerFactory.getLogger(UserServiceImpl.class);
   
	private UserRepository userRepository;
 
	private UserMapper userMapper;
	
	private PasswordEncoder passwordEncoder;
	
	private JwtUtil jwtUtil;
	
	private AuthMapper authMapper;
    
	public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, JwtUtil jwtUtil,AuthMapper authMapper) {
		this.userRepository=userRepository;
		this.userMapper=userMapper;
		this.passwordEncoder=passwordEncoder;
		this.jwtUtil=jwtUtil;
		this.authMapper=authMapper;
	}
	
	
	@Override
	public UserResponseDto register(UserCreateRequestDto dto) {
		 log.info("Attempting to register user with email: {}", dto.getEmail());
		if(userRepository.existsByEmail(dto.getEmail())) {
			log.warn("Registration failed: Email already exists - {}", dto.getEmail());
			throw new ResourceAlreadyExistsException("Email already registered");
		}
		User user=userMapper.toEntity(dto);
		User saved=userRepository.save(user);
		log.info("User registered successfully with id: {}", saved.getId());
		return userMapper.toDto(saved);
	}

	@Override
	public LoginResponseDto login(LoginRequestDto dto) {
		log.info("User login attempt for email: {}", dto.getEmail());
		User user=userRepository.findByEmail(dto.getEmail()).orElseThrow(
				()-> new ResourceNotFoundException("Invalid Credentials"));
		boolean matches=passwordEncoder.matches(dto.getPassword(), user.getPassword());
		if(!matches) {
			log.warn("Login failed: Invalid password for email - {}", dto.getEmail());
			throw new ResourceNotFoundException("Invalid Credentials");
			
		}
		
		String token= jwtUtil.generateTokens(user.getId(), user.getEmail(),user.getRole());
		log.info("User logged in successfully: {}", dto.getEmail());
		return authMapper.toLoginResponse(user, token);
		
	}

	@Override
	public UserResponseDto getById(Long id) {
		log.info("Fetching user with id: {}", id);
		User user=userRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("User not found with id:"+id));
		log.info("User fetched successfully with id: {}", id);
		return userMapper.toDto(user);
	}

}
