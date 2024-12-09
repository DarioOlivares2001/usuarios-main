package com.microrecetas.usuarios.service;

import com.microrecetas.usuarios.model.User;
import com.microrecetas.usuarios.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;  // Servicio que estamos probando

    @Mock
    private UserRepository userRepository;  // Repositorio que será simulado (mockeado)

    @Mock
    private PasswordEncoder passwordEncoder;  // Mock del codificador de contraseñas

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa los mocks antes de cada prueba
        user = new User();
        user.setUsername("testuser");
        user.setPassword("plainpassword");
    }

    // Test para registrar un usuario
 

    // Test para verificar que la contraseña esté correctamente codificada
    @Test
    void testPasswordIsEncoded() {
        // Simulamos la codificación de la contraseña
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedpassword");

        // Llamamos al método del servicio
        userService.registerUser(user);

        // Verificamos que la contraseña esté correctamente codificada
        assertEquals("encodedpassword", user.getPassword());
    }

    // Test para verificar que el repositorio 'save' sea llamado una vez
    @Test
    void testUserRepositorySaveCalled() {
        // Simulamos la codificación de la contraseña
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedpassword");

        // Llamamos al método del servicio
        userService.registerUser(user);

        // Verificamos que el repositorio fue llamado con el usuario
        verify(userRepository, times(1)).save(user);
    }

    // Test para verificar que la contraseña no sea guardada como texto plano
    @Test
    void testPasswordNotSavedInPlainText() {
        // Simulamos la codificación de la contraseña
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedpassword");

        // Llamamos al método del servicio
        userService.registerUser(user);

        // Verificamos que la contraseña no sea la original (texto plano)
        assertNotEquals("plainpassword", user.getPassword());
        // Verificamos que la contraseña sea la versión codificada
        assertEquals("encodedpassword", user.getPassword());
    }
}
