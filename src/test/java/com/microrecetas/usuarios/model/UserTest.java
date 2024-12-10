package com.microrecetas.usuarios.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testSetAndGetId() {
        User user = new User();
        Long expectedId = 1L;

        user.setId(expectedId);
        Long actualId = user.getId();

        assertEquals(expectedId, actualId, "El ID debería ser igual al valor asignado.");
    }

    @Test
    void testSetAndGetUsername() {
        User user = new User();
        String expectedUsername = "testUser";

        user.setUsername(expectedUsername);
        String actualUsername = user.getUsername();

        assertEquals(expectedUsername, actualUsername, "El nombre de usuario debería ser igual al valor asignado.");
    }

    @Test
    void testSetAndGetPassword() {
        User user = new User();
        String expectedPassword = "securePassword";

        user.setPassword(expectedPassword);
        String actualPassword = user.getPassword();

        assertEquals(expectedPassword, actualPassword, "La contraseña debería ser igual al valor asignado.");
    }

    @Test
    void testDefaultConstructor() {
        User user = new User();

        assertNull(user.getId(), "El ID debería ser nulo por defecto.");
        assertNull(user.getUsername(), "El nombre de usuario debería ser nulo por defecto.");
        assertNull(user.getPassword(), "La contraseña debería ser nula por defecto.");
    }
}
