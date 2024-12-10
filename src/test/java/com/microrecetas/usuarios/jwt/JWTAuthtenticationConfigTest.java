package com.microrecetas.usuarios.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JWTAuthtenticationConfigTest {

    private JWTAuthtenticationConfig jwtAuthenticationConfig;

    @BeforeEach
    void setUp() {
        jwtAuthenticationConfig = new JWTAuthtenticationConfig();
    }

    @Test
    void testGetJWTToken_ValidToken() {
        // Arrange
        String username = "testUser";

        // Act
        String token = jwtAuthenticationConfig.getJWTToken(username);

        // Assert
        assertNotNull(token);
        assertTrue(token.startsWith("Bearer "));

        // Parse the token and validate its claims
        String actualToken = token.replace("Bearer ", "");
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Constants.getSigningKey(Constants.SUPER_SECRET_KEY))
                .build()
                .parseClaimsJws(actualToken)
                .getBody();

        assertEquals(username, claims.getSubject());
        assertNotNull(claims.get("authorities"));

        // Check issuedAt and expiration
        Date issuedAt = claims.getIssuedAt();
        Date expiration = claims.getExpiration();
        assertTrue(issuedAt.before(expiration));
    }

    @Test
    void testGetJWTToken_ContainsAuthorities() {
        // Arrange
        String username = "testUser";

        // Act
        String token = jwtAuthenticationConfig.getJWTToken(username);

        // Parse the token
        String actualToken = token.replace("Bearer ", "");
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Constants.getSigningKey(Constants.SUPER_SECRET_KEY))
                .build()
                .parseClaimsJws(actualToken)
                .getBody();

        @SuppressWarnings("unchecked")
        List<String> authorities = (List<String>) claims.get("authorities");

        // Assert
        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains("ROLE_USER"));
    }

    @Test
    void testGetJWTToken_ExpirationTime() {
        // Arrange
        String username = "testUser";

        // Act
        String token = jwtAuthenticationConfig.getJWTToken(username);

        // Parse the token
        String actualToken = token.replace("Bearer ", "");
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Constants.getSigningKey(Constants.SUPER_SECRET_KEY))
                .build()
                .parseClaimsJws(actualToken)
                .getBody();

        // Assert expiration time
        Date issuedAt = claims.getIssuedAt();
        Date expiration = claims.getExpiration();
        long expirationTime = expiration.getTime() - issuedAt.getTime();

        assertEquals(Constants.TOKEN_EXPIRATION_TIME, expirationTime);
    }
}
