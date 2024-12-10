package com.microrecetas.usuarios.service;

import com.microrecetas.usuarios.model.Receta;
import com.microrecetas.usuarios.repository.RecetaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class RecetaServiceTest {

    @Mock
    private RecetaRepository recetaRepository;

    @InjectMocks
    private RecetaService recetaService;

    private Receta receta;

    @BeforeEach
    void setUp() {
        receta = new Receta();
        receta.setId(1L);
        receta.setNombre("Receta 1");
        receta.setTipoCocina("Mexicana");
        receta.setPaisOrigen("México");
        receta.setDificultad("Fácil");
        receta.setInstrucciones("Cocinar en 10 minutos");
        receta.setTiempoCoccion(10);
        receta.setFotografia("url_imagen");
    }

    @Test
    void testObtenerTodasLasRecetas() {
        List<Receta> recetas = Arrays.asList(receta);
        when(recetaRepository.findAll()).thenReturn(recetas);

        List<Receta> result = recetaService.obtenerTodasLasRecetas();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(recetaRepository, times(1)).findAll();
    }

    @Test
    void testObtenerRecetaPorId() {
        when(recetaRepository.findById(1L)).thenReturn(Optional.of(receta));

        Optional<Receta> result = recetaService.obtenerRecetaPorId(1L);

        assertTrue(result.isPresent());
        assertEquals("Receta 1", result.get().getNombre());
        verify(recetaRepository, times(1)).findById(1L);
    }

    @Test
    void testCrearReceta() {
        when(recetaRepository.save(receta)).thenReturn(receta);

        Receta result = recetaService.crearReceta(receta);

        assertNotNull(result);
        assertEquals("Receta 1", result.getNombre());
        verify(recetaRepository, times(1)).save(receta);
    }

    @Test
    void testActualizarReceta() {
        Receta recetaActualizada = new Receta();
        recetaActualizada.setNombre("Receta Actualizada");
        recetaActualizada.setTipoCocina("Italiana");
        recetaActualizada.setPaisOrigen("Italia");
        recetaActualizada.setDificultad("Media");
        recetaActualizada.setInstrucciones("Cocinar en 20 minutos");
        recetaActualizada.setTiempoCoccion(20);
        
        recetaActualizada.setFotografia("url_imagen_actualizada");

        when(recetaRepository.findById(1L)).thenReturn(Optional.of(receta));
        when(recetaRepository.save(any(Receta.class))).thenReturn(recetaActualizada);

        Receta result = recetaService.actualizarReceta(1L, recetaActualizada);

        assertNotNull(result);
        assertEquals("Receta Actualizada", result.getNombre());
        assertEquals("Italiana", result.getTipoCocina());
        verify(recetaRepository, times(1)).findById(1L);
        verify(recetaRepository, times(1)).save(any(Receta.class));
    }

    @Test
    void testEliminarReceta() {
        doNothing().when(recetaRepository).deleteById(1L);

        recetaService.eliminarReceta(1L);

        verify(recetaRepository, times(1)).deleteById(1L);
    }

    @Test
    void testObtenerNombresDeRecetas() {
        List<String> nombres = Arrays.asList("Receta 1", "Receta 2");
        when(recetaRepository.findAllNombres()).thenReturn(nombres);

        List<String> result = recetaService.obtenerNombresDeRecetas();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains("Receta 1"));
        assertTrue(result.contains("Receta 2"));
        verify(recetaRepository, times(1)).findAllNombres();
    }

    @Test
    void testGuardarRecetas() {
        List<Receta> recetas = Arrays.asList(receta, receta);
        when(recetaRepository.saveAll(recetas)).thenReturn(recetas);

        recetaService.guardarRecetas(recetas);

        verify(recetaRepository, times(1)).saveAll(recetas);
    }

    @Test
    void testBuscarRecetaPorNombre() {
        when(recetaRepository.findByNombre("Receta 1")).thenReturn(receta);

        Receta result = recetaService.buscarRecetaPorNombre("Receta 1");

        assertNotNull(result);
        assertEquals("Receta 1", result.getNombre());
        verify(recetaRepository, times(1)).findByNombre("Receta 1");
    }
}
