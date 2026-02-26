package com.artichourey.ecommerce.userservice.repository;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.artichourey.ecommerce.userservice.entity.User;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Should return true when email exists")
    void existsByEmail_ShouldReturnTrue() {

        // Arrange
        User user = User.builder()
                .name("Jam")
                .email("jam@test.com")
                .password("encodedPassword")
                .roles("USER_ROLE")
                .phone("1234567899")
                .build();

        userRepository.save(user);

        // Act
        boolean exists = userRepository.existsByEmail("jam@test.com");

        // Assert
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should find user by email")
    void findByEmail_ShouldReturnUser() {

        // Arrange
        User user = User.builder()
                .name("Aliya")
                .email("aliya@test.com")
                .password("encodedPassword")
                .roles("USER_ROLE")
                .phone("1234567890")
                
                .build();

        userRepository.save(user);

        // Act
        Optional<User> found = userRepository.findByEmail("aliya@test.com");

        // Assert
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Aliya");
        assertThat(found.get().getEmail()).isEqualTo("aliya@test.com");
    }
}
