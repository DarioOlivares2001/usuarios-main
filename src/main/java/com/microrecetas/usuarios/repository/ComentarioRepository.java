package com.microrecetas.usuarios.repository;

import com.microrecetas.usuarios.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    // Puedes agregar métodos de consulta personalizados si es necesario
}
