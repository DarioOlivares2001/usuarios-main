package com.microrecetas.usuarios.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "COMENTARIOS")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comentario", columnDefinition = "TEXT")
    private String comentario;

    @Column(name = "valoracion")
    private int valoracion;  // De 1 a 19

    @ManyToOne
    @JoinColumn(name = "receta_id", nullable = false)
    @JsonBackReference  // Evita la recursión infinita
    private Receta receta;

    // Constructor vacío
    public Comentario() {}

    // Constructor con parámetros
    public Comentario(String comentario, int valoracion, Receta receta) {
        this.comentario = comentario;
        this.valoracion = valoracion;
        this.receta = receta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    public Receta getReceta() {
        return receta;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }

    // Getters y Setters
    // ...
}
