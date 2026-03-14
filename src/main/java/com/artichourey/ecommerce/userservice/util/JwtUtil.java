package com.artichourey.ecommerce.userservice.util;

import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.artichourey.ecommerce.userservice.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtUtil {

	private final SecretKey key;
	private final long jwtExpirationMs;
	
	
	public JwtUtil(@Value("${jwt.secret}")String secret, @Value("${jwt.expiration-ms}")long jwtExpirationMs) {
		this.key=Keys.hmacShaKeyFor(secret.getBytes());
		this.jwtExpirationMs=jwtExpirationMs;
	}
	public String generateTokens(Long userId, String email, Role role) {
		long now=System.currentTimeMillis();
		return Jwts.builder()
		.subject(String.valueOf(userId))
		.claim("email", email)
		.claim("role", role.name())
		.issuedAt(new Date(now))
		.expiration(new Date(now + jwtExpirationMs))
		.signWith(key)
		.compact();
		
	}
	public Jws<Claims> validateToken(String token){
		return Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
	}
	public Long getUserIdFromToken(String token) {
		Claims claims=validateToken(token).getBody();
		return Long.valueOf(claims.getSubject());
		
	}
}
