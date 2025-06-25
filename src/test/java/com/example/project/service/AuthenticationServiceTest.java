package com.example.project.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationServiceTest {
    private AuthenticationService authenticationService;
    private String jwtSecret = "MySuperSecretKeyForJwtThatIsAtLeast32BytesLong!";
    private String token;

    @BeforeEach
    void setUp() {
        authenticationService = new AuthenticationService();
        authenticationService.jwtSecret = jwtSecret;
        authenticationService.init();
        token = Jwts.builder()
                .setSubject("testuser")
                .claim("roles", List.of("ROLE_USER", "ROLE_ADMIN"))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    @Test
    @DisplayName("validateToken: valid token")
    void validateToken_valid() {
        assertTrue(authenticationService.validateToken(token));
    }

    @Test
    @DisplayName("validateToken: invalid token")
    void validateToken_invalid() {
        assertFalse(authenticationService.validateToken(token + "invalid"));
    }

    @Test
    @DisplayName("getUsernameFromToken: extracts subject")
    void getUsernameFromToken() {
        assertEquals("testuser", authenticationService.getUsernameFromToken(token));
    }

    @Test
    @DisplayName("getAuthoritiesFromToken: extracts roles")
    void getAuthoritiesFromToken() {
        List<GrantedAuthority> authorities = authenticationService.getAuthoritiesFromToken(token);
        assertEquals(2, authorities.size());
        assertTrue(authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
        assertTrue(authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    @DisplayName("getAuthentication: builds Authentication object")
    void getAuthentication() {
        Authentication auth = authenticationService.getAuthentication(token);
        assertNotNull(auth);
        assertEquals("testuser", ((User) auth.getPrincipal()).getUsername());
        assertEquals(token, auth.getCredentials());
    }
}
