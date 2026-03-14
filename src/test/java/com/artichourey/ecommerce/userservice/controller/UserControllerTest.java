package com.artichourey.ecommerce.userservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.artichourey.ecommerce.userservice.dto.LoginRequestDto;
import com.artichourey.ecommerce.userservice.dto.LoginResponseDto;
import com.artichourey.ecommerce.userservice.dto.UserCreateRequestDto;
import com.artichourey.ecommerce.userservice.dto.UserResponseDto;
import com.artichourey.ecommerce.userservice.enums.Role;
import com.artichourey.ecommerce.userservice.service.UserService;
import com.artichourey.ecommerce.userservice.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {
	
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    
    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void register_ShouldReturnCreated() throws Exception {
        UserCreateRequestDto request = new UserCreateRequestDto();
        request.setName("jam");
        request.setEmail("jam@test.com");
        request.setPassword("123");
        request.setPhone("1234567890");
        request.setPassword("123456");
        request.setRole(Role.ROLE_USER);
        

        UserResponseDto response = new UserResponseDto();
        response.setId(1L);
        response.setEmail("jam@test.com");

        when(userService.register(any(UserCreateRequestDto.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("jam@test.com"));
    }

    
    @Test
    void login_ShouldReturnOk() throws Exception {
        LoginRequestDto request = new LoginRequestDto();
        request.setEmail("jam@test.com");
        request.setPassword("123");

        LoginResponseDto response = new LoginResponseDto();

        when(userService.login(any(LoginRequestDto.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void getById_ShouldReturnUser() throws Exception {
        UserResponseDto response = new UserResponseDto();
        response.setId(1L);
        response.setEmail("jam@test.com");

        when(userService.getById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }
}

