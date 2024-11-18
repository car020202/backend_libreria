package com.libreria.repository;

import com.libreria.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTituloAndAutor(String titulo, String autor);
}
