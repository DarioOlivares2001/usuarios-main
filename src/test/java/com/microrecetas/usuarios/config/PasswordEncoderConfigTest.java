package com.microrecetas.usuarios.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class PasswordEncoderConfigTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testPasswordEncoderBeanCreation() {
        // Verifica que el PasswordEncoder ha sido creado e inyectado correctamente
        assertNotNull(passwordEncoder, "PasswordEncoder bean should be created and injected into the context");
    }

    @Test
    void testPasswordEncoding() {
        // Verifica que el PasswordEncoder puede cifrar una contraseña
        String rawPassword = "myPassword";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        
        // Verifica que la contraseña cifrada no sea igual a la contraseña sin cifrar
        assertNotNull(encodedPassword, "Encoded password should not be null");
        assertNotEquals(rawPassword, encodedPassword, "Encoded password should be different from raw password");
    }

    @Test
    void testPasswordEncoderCannotBeReversed() {
        // Verifica que el PasswordEncoder no pueda revertir la contraseña
        String rawPassword = "myPassword";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        
        // No podemos revertir la contraseña, solo verificar que el hash sea diferente al original
        assertNotEquals(rawPassword, encodedPassword, "Encoded password should not be equal to raw password");
    }

    @Test
    void testPasswordEncoderGeneratesDifferentHashesForSamePassword() {
        // Verifica que el PasswordEncoder genere diferentes hashes para la misma contraseña
        String rawPassword = "myPassword";
        String encodedPassword1 = passwordEncoder.encode(rawPassword);
        String encodedPassword2 = passwordEncoder.encode(rawPassword);

        // Los hashes generados para la misma contraseña deberían ser diferentes
        assertNotEquals(encodedPassword1, encodedPassword2, "Encoded passwords should be different for the same raw password");
    }

    @Test
    void testPasswordMatches() {
        // Verifica que el PasswordEncoder pueda verificar que la contraseña coincide con el hash
        String rawPassword = "myPassword";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        
        // Verifica que la contraseña sin cifrar coincide con la contraseña cifrada
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword), "Password should match the encoded password");
    }
}
