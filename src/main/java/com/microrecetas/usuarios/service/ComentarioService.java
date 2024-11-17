package com.microrecetas.usuarios.service;

import com.microrecetas.usuarios.model.Comentario;
import com.microrecetas.usuarios.repository.ComentarioRepository;
import org.springframework.stereotype.Service;

@Service
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;

    public ComentarioService(ComentarioRepository comentarioRepository) {
        this.comentarioRepository = comentarioRepository;
    }

    public Comentario crearComentario(Comentario comentario) {
        return comentarioRepository.save(comentario);
    }
}
