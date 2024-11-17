package com.microrecetas.usuarios.jwt;

import io.jsonwebtoken.Jwts;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.microrecetas.usuarios.jwt.Constants.*;


@Configuration
public class JWTAuthtenticationConfig {

   public String getJWTToken(String username) {
    List<GrantedAuthority> grantedAuthorities = AuthorityUtils
        .commaSeparatedStringToAuthorityList("ROLE_USER");

    String token = Jwts.builder()
        .setSubject(username)  // Usar setSubject en lugar de subject
        .claim("authorities", grantedAuthorities.stream()  // Usar setClaim en lugar de claim
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList()))
        .setIssuedAt(new Date(System.currentTimeMillis()))  // Usar setIssuedAt en lugar de issuedAt
        .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))  // Usar setExpiration en lugar de expiration
        .signWith(getSigningKey(SUPER_SECRET_KEY))  // Este método se mantiene igual
        .compact();

    return TOKEN_BEARER_PREFIX + token;
}
}