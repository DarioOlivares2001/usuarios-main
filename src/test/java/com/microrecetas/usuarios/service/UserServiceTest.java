package com.microrecetas.usuarios.service;

import com.microrecetas.usuarios.model.User;
import com.microrecetas.usuarios.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

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

    

    @Test
    void testUserIsSavedInRepository() {
        // Simulamos que el repositorio no devuelve ningún usuario con el nombre de usuario
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        // Simulamos la codificación de la contraseña
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedpassword");

        // Llamamos al método del servicio
        userService.registerUser(user);

        // Verificamos que el repositorio `save` haya sido llamado con el usuario
        verify(userRepository, times(1)).save(user);

        // Verificamos que la contraseña esté codificada antes de guardar
        assertEquals("encodedpassword", user.getPassword());
    }

   @Test
    void testPasswordIsEncodedEvenIfUserDoesNotExist() {
        // Simulamos que el repositorio no devuelve ningún usuario con el nombre de usuario
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        // Simulamos la codificación de la contraseña
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedpassword");

        // Llamamos al método del servicio
        userService.registerUser(user);

        // Verificamos que la contraseña esté correctamente codificada
        assertEquals("encodedpassword", user.getPassword());
    }


    @Test
    void testLoginUserSuccess() {
        // Simulamos que el repositorio devuelve un usuario con el nombre de usuario
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        // Simulamos que la contraseña ingresada coincide con la almacenada
        when(passwordEncoder.matches("plainpassword", user.getPassword())).thenReturn(true);

        // Llamamos al método del servicio
        User loggedInUser = userService.loginUser(user.getUsername(), "plainpassword");

        // Verificamos que el usuario devuelto sea el mismo que el que simulamos
        assertEquals(user, loggedInUser);
    }
   

    @Test
    void testLoginUserWrongPassword() {
        // Simulamos que el repositorio devuelve un usuario con el nombre de usuario
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        // Simulamos que la contraseña ingresada no coincide con la almacenada
        when(passwordEncoder.matches("wrongpassword", user.getPassword())).thenReturn(false);

        Executable loginaction = () -> userService.loginUser(user.getUsername(), "plainpassword");

        // Llamamos al método del servicio y verificamos que se lance la excepción
        assertThrows(UsernameNotFoundException.class, loginaction);
    }

    @Test
    void testLoginUserNotFound() {
        // Simulamos que el repositorio no devuelve ningún usuario con el nombre de usuario
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());

         // Usamos un bloque separado para simplificar la lambda y cumplir con la recomendación
         Executable loginaction = () -> userService.loginUser(user.getUsername(), "plainpassword");
    
        // Llamamos al método del servicio y verificamos que se lance la excepción
        assertThrows(UsernameNotFoundException.class, loginaction);
    }

    @Test
    void testUpdateUser() {
        // Simulamos que el repositorio devuelve un usuario con el nombre de usuario
        User updatedUser = new User();
        updatedUser.setUsername("updateduser");
        updatedUser.setPassword("newpassword");
        
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(updatedUser.getPassword())).thenReturn("encodednewpassword");

        // Llamamos al método del servicio
        userService.updateUser(user.getUsername(), updatedUser);

        // Verificamos que la contraseña haya sido actualizada y codificada
        assertEquals("encodednewpassword", user.getPassword());
        assertEquals("updateduser", user.getUsername());

        // Verificamos que el repositorio `save` haya sido llamado
        verify(userRepository, times(1)).save(user);
    }


    @Test
    void testDeleteUser() {
        // Simulamos que el repositorio devuelve un usuario con el nombre de usuario
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        // Llamamos al método del servicio
        userService.deleteUser(user.getUsername());

        // Verificamos que el repositorio `delete` haya sido llamado con el usuario
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void testDeleteUserNotFound() {
        // Simulamos que el repositorio no devuelve ningún usuario con el nombre de usuario
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());


        // Usamos un bloque separado para simplificar la lambda y cumplir con la recomendación
        Executable deleteUserAction = () -> userService.deleteUser(user.getUsername());


        // Llamamos al método del servicio y verificamos que se lance la excepción
        assertThrows(UsernameNotFoundException.class, deleteUserAction);
    }


    @Test
    void testGetAllUsers() {
        // Simulamos que el repositorio devuelve una lista de usuarios
        User anotherUser = new User();
        anotherUser.setUsername("anotheruser");
        anotherUser.setPassword("password");
        
        when(userRepository.findAll()).thenReturn(List.of(user, anotherUser));

        // Llamamos al método del servicio
        List<User> users = userService.getAllUsers();

        // Verificamos que la lista contenga los usuarios esperados
        assertEquals(2, users.size());
        assertTrue(users.contains(user));
        assertTrue(users.contains(anotherUser));
    }
}
