package com.microrecetas.usuarios.controller;

import com.microrecetas.usuarios.model.Comentario;
import com.microrecetas.usuarios.model.Receta;
import com.microrecetas.usuarios.service.ComentarioService;
import com.microrecetas.usuarios.service.RecetaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecetasControllerTest {

    @InjectMocks
    private RecetasController recetasController;

    @Mock
    private RecetaService recetaService;

    @Mock
    private ComentarioService comentarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerTodasLasRecetas() {
        // Configurar datos simulados
        List<Receta> recetasSimuladas = new ArrayList<>();
        recetasSimuladas.add(new Receta("Receta1", "Tipo1", "Chile", "Fácil", "Instrucciones 1", 30, List.of("Ingrediente1"), "foto1.jpg", "video1.com"));
        recetasSimuladas.add(new Receta("Receta2", "Tipo2", "España", "Media", "Instrucciones 2", 40, List.of("Ingrediente2"), "foto2.jpg", "video2.com"));

        when(recetaService.obtenerTodasLasRecetas()).thenReturn(recetasSimuladas);

        // Ejecutar el método
        List<Receta> resultado = recetasController.obtenerTodasLasRecetas();

        // Verificar resultados
        assertEquals(2, resultado.size());
        verify(recetaService, times(1)).obtenerTodasLasRecetas();
    }

    @Test
    void testObtenerRecetaPorId() {
        // Configurar datos simulados
        Receta recetaSimulada = new Receta("Receta1", "Tipo1", "Chile", "Fácil", "Instrucciones 1", 30, List.of("Ingrediente1"), "foto1.jpg", "video1.com");

        when(recetaService.obtenerRecetaPorId(1L)).thenReturn(Optional.of(recetaSimulada));

        // Ejecutar el método
        Receta resultado = recetasController.obtenerRecetaPorId(1L);

        // Verificar resultados
        assertNotNull(resultado);
        assertEquals("Receta1", resultado.getNombre());
        verify(recetaService, times(1)).obtenerRecetaPorId(1L);
    }

    @Test
    void testObtenerRecetaPorIdNoEncontrada() {
        // Configurar datos simulados
        when(recetaService.obtenerRecetaPorId(1L)).thenReturn(Optional.empty());

        // Ejecutar y verificar excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            recetasController.obtenerRecetaPorId(1L);
        });

        assertEquals("Receta no encontrada", exception.getMessage());
        verify(recetaService, times(1)).obtenerRecetaPorId(1L);
    }

    @Test
    void testBuscarRecetaPorNombre() {
        // Configurar datos simulados
        Receta recetaSimulada = new Receta("Receta1", "Tipo1", "Chile", "Fácil", "Instrucciones 1", 30, List.of("Ingrediente1"), "foto1.jpg", "video1.com");

        when(recetaService.buscarRecetaPorNombre("Receta1")).thenReturn(recetaSimulada);

        // Ejecutar el método
        Receta resultado = recetasController.buscarRecetaPorNombre("Receta1");

        // Verificar resultados
        assertNotNull(resultado);
        assertEquals("Receta1", resultado.getNombre());
        verify(recetaService, times(1)).buscarRecetaPorNombre("Receta1");
    }

    @Test
    void testBuscarRecetaPorNombreNoEncontrada() {
        // Configurar datos simulados
        when(recetaService.buscarRecetaPorNombre("RecetaInexistente")).thenReturn(null);

        // Ejecutar el método
        Receta resultado = recetasController.buscarRecetaPorNombre("RecetaInexistente");

        // Verificar resultados
        assertNull(resultado);
        verify(recetaService, times(1)).buscarRecetaPorNombre("RecetaInexistente");
    }

    
    @Test
    void testCrearReceta() {
        Receta recetaNueva = new Receta("Receta3", "Tipo3", "México", "Difícil", "Instrucciones 3", 50, List.of("Ingrediente3"), "foto3.jpg", "video3.com");

        when(recetaService.crearReceta(recetaNueva)).thenReturn(recetaNueva);

        Receta resultado = recetasController.crearReceta(recetaNueva);

        assertNotNull(resultado);
        assertEquals("Receta3", resultado.getNombre());
        verify(recetaService, times(1)).crearReceta(recetaNueva);
    }

  
    // Test para cuando no se encuentra la receta al agregar un comentario
    @Test
    void testAgregarComentarioRecetaNoEncontrada() {
        Comentario comentarioSimulado = new Comentario("Muy buena receta!",2, new Receta());

        when(recetaService.obtenerRecetaPorId(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            recetasController.agregarComentario(comentarioSimulado);
        });

        assertEquals("Receta no encontrada", exception.getMessage());
        verify(comentarioService, times(0)).crearComentario(comentarioSimulado);
    }

  
}
