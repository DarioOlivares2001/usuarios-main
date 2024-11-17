package com.microrecetas.usuarios.controller;

import com.microrecetas.usuarios.model.Comentario;
import com.microrecetas.usuarios.model.Receta;
import com.microrecetas.usuarios.service.ComentarioService;
import com.microrecetas.usuarios.service.RecetaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recetas")
public class RecetasController {

    @Autowired
    private RecetaService recetaService;

    @Autowired
    private ComentarioService comentarioService;

 // Endpoint para traer todas las recetas
    @GetMapping("/todas")
    public List<Receta> obtenerTodasLasRecetas() {
        return recetaService.obtenerTodasLasRecetas();
    }

    // Endpoint para traer una receta por ID
    @GetMapping("/{id}")
    public Receta obtenerRecetaPorId(@PathVariable Long id) {
        return recetaService.obtenerRecetaPorId(id)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));
    }


    @GetMapping("/buscar")
    public Receta buscarRecetaPorNombre(@RequestParam(value = "nombre") String nombre) {
        Optional<Receta> recetaOptional = Optional.ofNullable(recetaService.buscarRecetaPorNombre(nombre));
        return recetaOptional.orElse(null); // Devuelve null si no se encuentra
    }

    // Endpoint para agregar una receta con foto y video
    @PostMapping("/crear")
    public Receta crearReceta(@RequestBody Receta receta) {
        return recetaService.crearReceta(receta);
    }

    // Endpoint para agregar un comentario a una receta
    @PostMapping("/comentarios")
    public Comentario agregarComentario(@RequestBody Comentario comentario) {
         // Obtener la receta correspondiente por ID (suponiendo que la receta tiene un ID)
        Receta receta = recetaService.obtenerRecetaPorId(comentario.getReceta().getId())
        .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

        // Asociar el comentario a la receta
        comentario.setReceta(receta);

        return comentarioService.crearComentario(comentario);
    }
}