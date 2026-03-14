package com.artichourey.ecommerce.userservice.entity;

import java.time.Instant;

import com.artichourey.ecommerce.userservice.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	@Column(nullable=false, unique=true)
	private String email;
	private String phone;
	@Column(nullable=false)
	private String password;
	
	@Enumerated(EnumType.STRING)
    private Role role;
	
	
	private Instant createdAt;
	private Instant updatedAt;
	
	@PrePersist
	public void prePersist() {
		createdAt=Instant.now();
		updatedAt=createdAt;
	}
	
    @PreUpdate
	public void preUpdate() {
		updatedAt=Instant.now();
		
	}
}
