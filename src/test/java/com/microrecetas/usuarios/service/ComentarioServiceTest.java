package com.microrecetas.usuarios.service;

import com.microrecetas.usuarios.model.Comentario;
import com.microrecetas.usuarios.repository.ComentarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ComentarioServiceTest {

    @InjectMocks
    private ComentarioService comentarioService;  // Servicio que estamos probando

    @Mock
    private ComentarioRepository comentarioRepository;  // Repositorio que será simulado (mockeado)

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa los mocks antes de cada prueba
    }

    // Test para crear un comentario
    @Test
    void testCrearComentario() {
        // Preparar el comentario que se va a guardar
        Comentario comentarioSimulado = new Comentario();
        comentarioSimulado.setComentario("Muy buena receta!");
        
        // Simular el comportamiento del repositorio
        when(comentarioRepository.save(comentarioSimulado)).thenReturn(comentarioSimulado);

        // Llamar al servicio
        Comentario resultado = comentarioService.crearComentario(comentarioSimulado);

        // Verificar que el comentario se guardó correctamente
        assertNotNull(resultado);  // Verifica que el resultado no sea nulo
        assertEquals("Muy buena receta!", resultado.getComentario());  // Verifica que el texto del comentario es el esperado

        // Verificar que el repositorio fue llamado una vez con el comentario
        verify(comentarioRepository, times(1)).save(comentarioSimulado);
    }

    // Test para verificar que se llama al repositorio cuando se crea un comentario
    @Test
    void testCrearComentarioRepositorioLlamado() {
        // Preparar un comentario
        Comentario comentarioSimulado = new Comentario();
        comentarioSimulado.setComentario("Excelente receta, me encantó.");

        // Llamar al método del servicio
        comentarioService.crearComentario(comentarioSimulado);

        // Verificar que el repositorio fue llamado una vez con el comentario
        verify(comentarioRepository, times(1)).save(comentarioSimulado);
    }
}
