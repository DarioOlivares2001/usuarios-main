package com.microrecetas.usuarios.repository;

import com.microrecetas.usuarios.model.Receta;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Long> {
    // Puedes agregar métodos de consulta personalizados si es necesario, por ejemplo:
    Receta findByNombre(String nombre);
    List<Receta> findByTipoCocina(String tipoCocina);
    @Query("SELECT r.nombre FROM Receta r")
    List<String> findAllNombres();
}
