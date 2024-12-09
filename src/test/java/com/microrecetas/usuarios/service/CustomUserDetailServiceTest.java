package com.microrecetas.usuarios.service;

import com.microrecetas.usuarios.model.User;
import com.microrecetas.usuarios.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;  // Servicio que estamos probando

    @Mock
    private UserRepository userRepository;  // Repositorio simulado

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa los mocks antes de cada prueba
        user = new User();
        user.setUsername("testuser");
        user.setPassword("encodedpassword");
    }

    // Test para verificar que se retorna el UserDetails cuando el usuario es encontrado
    @Test
    void testLoadUserByUsername_UserFound() {
        // Simulamos que el repositorio encuentra el usuario
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        // Llamamos al método de carga de usuario
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("testuser");

        // Verificamos que el UserDetails no sea nulo y tenga el nombre de usuario correcto
        assertNotNull(userDetails);
        assertEquals("testuser", userDetails.getUsername());
        assertEquals("encodedpassword", userDetails.getPassword());

        // Verificamos que se asignen correctamente los roles
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_USER")));
    }

    // Test para verificar que se lanza una excepción cuando el usuario no es encontrado
    @Test
    void testLoadUserByUsername_UserNotFound() {
        // Simulamos que el repositorio no encuentra el usuario
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        // Verificamos que se lance una excepción cuando el usuario no es encontrado
        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername("testuser");
        });
    }

    // Test para verificar que se llama al repositorio 'findByUsername' exactamente una vez
    @Test
    void testUserRepositoryFindByUsernameCalled() {
        // Simulamos que el repositorio encuentra el usuario
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        // Llamamos al método de carga de usuario
        customUserDetailsService.loadUserByUsername("testuser");

        // Verificamos que el repositorio haya sido llamado exactamente una vez
        verify(userRepository, times(1)).findByUsername("testuser");
    }
}
