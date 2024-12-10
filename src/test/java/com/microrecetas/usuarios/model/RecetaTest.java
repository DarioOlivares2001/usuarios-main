package com.microrecetas.usuarios.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecetaTest {

    @Test
    void testDefaultConstructor() {
        Receta receta = new Receta();

        assertNull(receta.getId(), "El ID debería ser nulo por defecto.");
        assertNull(receta.getNombre(), "El nombre debería ser nulo por defecto.");
        assertNull(receta.getTipoCocina(), "El tipo de cocina debería ser nulo por defecto.");
        assertNull(receta.getPaisOrigen(), "El país de origen debería ser nulo por defecto.");
        assertNull(receta.getDificultad(), "La dificultad debería ser nula por defecto.");
        assertNull(receta.getInstrucciones(), "Las instrucciones deberían ser nulas por defecto.");
        assertEquals(0, receta.getTiempoCoccion(), "El tiempo de cocción debería ser 0 por defecto.");
        assertNull(receta.getIngredientes(), "Los ingredientes deberían ser nulos por defecto.");
        assertNull(receta.getFotografia(), "La fotografía debería ser nula por defecto.");
        assertNull(receta.getVideoUrl(), "El video URL debería ser nulo por defecto.");
        assertNull(receta.getComentarios(), "Los comentarios deberían ser nulos por defecto.");
    }

    @Test
    void testParameterizedConstructor() {
        List<String> ingredientes = Arrays.asList("Harina", "Huevo", "Leche");
        String nombre = "Tarta de Manzana";
        String tipoCocina = "Postre";
        String paisOrigen = "Francia";
        String dificultad = "Media";
        String instrucciones = "Mezclar todo y hornear.";
        int tiempoCoccion = 45;
        String fotografia = "imagen.jpg";
        String videoUrl = "https://youtube.com/video";

        Receta receta = new Receta(
                nombre,
                tipoCocina,
                paisOrigen,
                dificultad,
                instrucciones,
                tiempoCoccion,
                ingredientes,
                fotografia,
                videoUrl
        );

        assertNull(receta.getId(), "El ID debería ser nulo en el constructor.");
        assertEquals(nombre, receta.getNombre());
        assertEquals(tipoCocina, receta.getTipoCocina());
        assertEquals(paisOrigen, receta.getPaisOrigen());
        assertEquals(dificultad, receta.getDificultad());
        assertEquals(instrucciones, receta.getInstrucciones());
        assertEquals(tiempoCoccion, receta.getTiempoCoccion());
        assertEquals(ingredientes, receta.getIngredientes());
        assertEquals(fotografia, receta.getFotografia());
        assertEquals(videoUrl, receta.getVideoUrl());
    }

    @Test
    void testSetAndGetId() {
        Receta receta = new Receta();
        Long expectedId = 1L;

        receta.setId(expectedId);

        assertEquals(expectedId, receta.getId());
    }

    @Test
    void testSetAndGetNombre() {
        Receta receta = new Receta();
        String expectedNombre = "Paella";

        receta.setNombre(expectedNombre);

        assertEquals(expectedNombre, receta.getNombre());
    }

    @Test
    void testSetAndGetIngredientes() {
        Receta receta = new Receta();
        List<String> ingredientes = Arrays.asList("Arroz", "Mariscos", "Azafrán");

        receta.setIngredientes(ingredientes);

        assertEquals(ingredientes, receta.getIngredientes());
    }

    @Test
    void testSetAndGetComentarios() {
        Receta receta = new Receta();
        List<Comentario> comentarios = Collections.emptyList();

        receta.setComentarios(comentarios);

        assertEquals(comentarios, receta.getComentarios());
    }

    @Test
    void testSetAndGetFotografia() {
        Receta receta = new Receta();
        String expectedFoto = "foto.jpg";

        receta.setFotografia(expectedFoto);

        assertEquals(expectedFoto, receta.getFotografia());
    }

    @Test
    void testSetAndGetVideoUrl() {
        Receta receta = new Receta();
        String expectedUrl = "https://youtube.com/video";

        receta.setVideoUrl(expectedUrl);

        assertEquals(expectedUrl, receta.getVideoUrl());
    }

    @Test
    void testSetAndGetTiempoCoccion() {
        Receta receta = new Receta();
        int tiempo = 30;

        receta.setTiempoCoccion(tiempo);

        assertEquals(tiempo, receta.getTiempoCoccion());
    }
}
