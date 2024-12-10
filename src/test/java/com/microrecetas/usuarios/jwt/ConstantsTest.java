package com.microrecetas.usuarios.jwt;

import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.*;

class ConstantsTest {

    @Test
    void testGetSigningKeyB64() {
        // Dado un valor de clave base64
        String base64Secret = Constants.SUPER_SECRET_KEY;

        // Cuando se obtiene la clave secreta con getSigningKeyB64
        SecretKey secretKey = Constants.getSigningKeyB64(base64Secret);

        // Entonces la clave secreta no debe ser nula
        assertNotNull(secretKey, "The secret key should not be null");

        // Verifica que el tipo de la clave secreta sea HMAC SHA
        assertEquals("HmacSHA512", secretKey.getAlgorithm(), "The secret key algorithm should be HmacSHA256");

        // Asegúrate de que la longitud de la clave sea válida (al menos 256 bits si es HMAC SHA-256)
        assertTrue(secretKey.getEncoded().length >= 32, "The length of the secret key should be at least 32 bytes");
    }
    

    @Test
    void testBase64Decoding() {
        // Dado el valor de la clave en base64
        String base64Secret = Constants.SUPER_SECRET_KEY;

        // Decodifica la clave con la función getSigningKeyB64
        byte[] decodedKey = Constants.getSigningKeyB64(base64Secret).getEncoded();

        // Luego, la clave decodificada debería ser un arreglo de bytes que no sea vacío
        assertNotNull(decodedKey, "Decoded key should not be null");
        assertTrue(decodedKey.length > 0, "Decoded key length should be greater than zero");
    }
}
