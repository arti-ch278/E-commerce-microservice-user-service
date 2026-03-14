package com.artichourey.ecommerce.userservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.artichourey.ecommerce.userservice.dto.LoginRequestDto;
import com.artichourey.ecommerce.userservice.dto.LoginResponseDto;
import com.artichourey.ecommerce.userservice.dto.UserCreateRequestDto;
import com.artichourey.ecommerce.userservice.dto.UserResponseDto;
import com.artichourey.ecommerce.userservice.entity.User;
import com.artichourey.ecommerce.userservice.enums.Role;
import com.artichourey.ecommerce.userservice.exception.ResourceAlreadyExistsException;
import com.artichourey.ecommerce.userservice.exception.ResourceNotFoundException;
import com.artichourey.ecommerce.userservice.mapper.AuthMapper;
import com.artichourey.ecommerce.userservice.mapper.UserMapper;
import com.artichourey.ecommerce.userservice.repository.UserRepository;
import com.artichourey.ecommerce.userservice.services.impl.UserServiceImpl;
import com.artichourey.ecommerce.userservice.util.JwtUtil;

@ExtendWith(MockitoExtension.class)
public class UserSeriveImplTest {
	 @Mock
	    private UserRepository userRepository;

	    @Mock
	    private UserMapper userMapper;

	    @Mock
	    private PasswordEncoder passwordEncoder;

	    @Mock
	    private JwtUtil jwtUtil;

	    @Mock
	    private AuthMapper authMapper;

	    @InjectMocks
	    private UserServiceImpl userService;
	    private User user;
	    private UserCreateRequestDto createDto;
	    private UserResponseDto responseDto;
	    
	    
	    @BeforeEach
	    void setup() {
	        user = User.builder()
	                .id(1L)
	                .name("Jam")
	                .email("jam@test.com")
	                .password("encodedPass")
	                .role(Role.ROLE_USER)
	                .build();

	        createDto = new UserCreateRequestDto();
	        createDto.setEmail("jam@test.com");
	        createDto.setPassword("123456");

	        responseDto = new UserResponseDto();
	        responseDto.setId(1L);
	        responseDto.setEmail("jam@test.com");
	    }
	    
	    @Test
	    void register_ShouldCreateUserSuccessfully() {
	        when(userRepository.existsByEmail(createDto.getEmail())).thenReturn(false);
	        when(userMapper.toEntity(createDto)).thenReturn(user);
	        when(userRepository.save(user)).thenReturn(user);
	        when(userMapper.toDto(user)).thenReturn(responseDto);

	        UserResponseDto result = userService.register(createDto);

	        assertNotNull(result);
	        assertEquals("jam@test.com", result.getEmail());
	        verify(userRepository, times(1)).save(user);
	    }
	   
	    @Test
	    void register_ShouldThrowException_WhenEmailExists() {
	        when(userRepository.existsByEmail(createDto.getEmail())).thenReturn(true);

	        assertThrows(ResourceAlreadyExistsException.class,
	                () -> userService.register(createDto));

	        verify(userRepository, never()).save(any());
	    }

	    @Test
	    void login_ShouldReturnToken_WhenCredentialsValid() {

	        LoginRequestDto loginDto = new LoginRequestDto();
	        loginDto.setEmail("jam@test.com");
	        loginDto.setPassword("123");

	        when(userRepository.findByEmail(loginDto.getEmail()))
	                .thenReturn(Optional.of(user));

	        when(passwordEncoder.matches(loginDto.getPassword(), user.getPassword()))
	                .thenReturn(true);

	        when(jwtUtil.generateTokens(anyLong(), anyString(), any(Role.class)))
	                .thenReturn("mocked-jwt-token");

	        LoginResponseDto loginResponse = new LoginResponseDto();

	        when(authMapper.toLoginResponse(user, "mocked-jwt-token"))
	                .thenReturn(loginResponse);

	        LoginResponseDto result = userService.login(loginDto);

	        assertNotNull(result);

	        verify(jwtUtil, times(1))
	                .generateTokens(user.getId(), user.getEmail(), user.getRole());
	    }
	    
	    @Test
	    void login_ShouldThrowException_WhenUserNotFound() {
	        LoginRequestDto loginDto = new LoginRequestDto();
	        loginDto.setEmail("wrong@test.com");

	        when(userRepository.findByEmail(loginDto.getEmail()))
	                .thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class,
	                () -> userService.login(loginDto));
	    } 
	    
	    @Test
	    void getById_ShouldReturnUser_WhenExists() {
	        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
	        when(userMapper.toDto(user)).thenReturn(responseDto);

	        UserResponseDto result = userService.getById(1L);

	        assertNotNull(result);
	        assertEquals(1L, result.getId());
	    }

	    @Test
	    void getById_ShouldThrowException_WhenNotFound() {
	        when(userRepository.findById(1L)).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class,
	                () -> userService.getById(1L));
	    }
	    
}
