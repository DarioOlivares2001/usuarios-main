package com.microrecetas.usuarios.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComentarioTest {

    @Test
    void testDefaultConstructor() {
        Comentario comentario = new Comentario();

        assertNull(comentario.getId(), "El ID debería ser nulo por defecto.");
        assertNull(comentario.getComentario(), "El comentario debería ser nulo por defecto.");
        assertEquals(0, comentario.getValoracion(), "La valoración debería ser 0 por defecto.");
        assertNull(comentario.getReceta(), "La receta debería ser nula por defecto.");
    }

    @Test
    void testParameterizedConstructor() {
        Receta receta = new Receta();
        String textoComentario = "Muy buena receta, fácil de seguir.";
        int valoracion = 10;

        Comentario comentario = new Comentario(textoComentario, valoracion, receta);

        assertNull(comentario.getId(), "El ID debería ser nulo en el constructor.");
        assertEquals(textoComentario, comentario.getComentario());
        assertEquals(valoracion, comentario.getValoracion());
        assertEquals(receta, comentario.getReceta());
    }

    @Test
    void testSetAndGetId() {
        Comentario comentario = new Comentario();
        Long expectedId = 1L;

        comentario.setId(expectedId);

        assertEquals(expectedId, comentario.getId());
    }

    @Test
    void testSetAndGetComentario() {
        Comentario comentario = new Comentario();
        String expectedComentario = "Excelente receta.";

        comentario.setComentario(expectedComentario);

        assertEquals(expectedComentario, comentario.getComentario());
    }

    @Test
    void testSetAndGetValoracion() {
        Comentario comentario = new Comentario();
        int expectedValoracion = 15;

        comentario.setValoracion(expectedValoracion);

        assertEquals(expectedValoracion, comentario.getValoracion());
    }

    @Test
    void testSetAndGetReceta() {
        Comentario comentario = new Comentario();
        Receta receta = new Receta();
        receta.setNombre("Tarta de Manzana");

        comentario.setReceta(receta);

        assertEquals(receta, comentario.getReceta());
        assertEquals("Tarta de Manzana", comentario.getReceta().getNombre());
    }
}
