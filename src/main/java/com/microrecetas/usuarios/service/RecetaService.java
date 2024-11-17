package com.microrecetas.usuarios.service;

import com.microrecetas.usuarios.model.Receta;
import com.microrecetas.usuarios.repository.RecetaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecetaService {

    private final RecetaRepository recetaRepository;


    public RecetaService(RecetaRepository recetaRepository) {
        this.recetaRepository = recetaRepository;
    }

    public List<Receta> obtenerTodasLasRecetas() {
        return recetaRepository.findAll();
    }

    public Optional<Receta> obtenerRecetaPorId(Long id) {
        return recetaRepository.findById(id);
    }

    public Receta crearReceta(Receta receta) {
        return recetaRepository.save(receta);
    }

    public Receta actualizarReceta(Long id, Receta recetaActualizada) {
        return recetaRepository.findById(id)
                .map(receta -> {
                    receta.setNombre(recetaActualizada.getNombre());
                    receta.setTipoCocina(recetaActualizada.getTipoCocina());
                    receta.setPaisOrigen(recetaActualizada.getPaisOrigen());
                    receta.setDificultad(recetaActualizada.getDificultad());
                    receta.setInstrucciones(recetaActualizada.getInstrucciones());
                    receta.setTiempoCoccion(recetaActualizada.getTiempoCoccion());
                    receta.setIngredientes(recetaActualizada.getIngredientes());
                    receta.setFotografia(recetaActualizada.getFotografia());
                    return recetaRepository.save(receta);
                }).orElseThrow(() -> new RuntimeException("Receta no encontrada"));
    }

    public void eliminarReceta(Long id) {
        recetaRepository.deleteById(id);
    }

     // Método para obtener solo los nombres de las recetas
     public List<String> obtenerNombresDeRecetas() {
        return recetaRepository.findAllNombres();
    }

    public void guardarRecetas(List<Receta> recetas) {
        recetaRepository.saveAll(recetas);
    }

    public Receta buscarRecetaPorNombre(String nombre) {
        return recetaRepository.findByNombre(nombre);
    }
}
