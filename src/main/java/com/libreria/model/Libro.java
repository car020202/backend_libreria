package com.libreria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "Libros", uniqueConstraints = @UniqueConstraint(columnNames = {"titulo", "autor"}))
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_libro")
    private Long id;

    @NotBlank(message = "El título no puede estar vacío.")
    @Size(max = 100, message = "El título no puede tener más de 100 caracteres.")
    private String titulo;

    @NotBlank(message = "El autor no puede estar vacío.")
    @Size(max = 100, message = "El autor no puede tener más de 100 caracteres.")
    private String autor;

    @NotBlank(message = "El género no puede estar vacío.")
    @Size(max = 50, message = "El género no puede tener más de 50 caracteres.")
    private String genero;

    @NotNull(message = "El estado no puede ser nulo.")
    @Min(value = 0, message = "El estado debe ser 0, 1 o 2.")
    @Max(value = 2, message = "El estado debe ser 0, 1 o 2.")
    private Integer estado;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }
}
