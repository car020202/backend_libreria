package com.libreria.service;

import com.libreria.model.Libro;
import com.libreria.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    public Libro createLibro(Libro libro) throws Exception {
        if (libroRepository.findByTituloAndAutor(libro.getTitulo(), libro.getAutor()).isPresent()) {
            throw new Exception("Ya existe un libro con este título y autor.");
        }
        return libroRepository.save(libro);
    }

    public List<Libro> getAllLibros() {
        return libroRepository.findAll();
    }

    public Libro updateLibro(Long id, Libro updatedLibro) throws Exception {
        Libro existingLibro = libroRepository.findById(id)
                .orElseThrow(() -> new Exception("Libro no encontrado."));
        existingLibro.setTitulo(updatedLibro.getTitulo());
        existingLibro.setAutor(updatedLibro.getAutor());
        existingLibro.setGenero(updatedLibro.getGenero());
        existingLibro.setEstado(updatedLibro.getEstado());
        return libroRepository.save(existingLibro);
    }

    public void deleteLibro(Long id) throws Exception {
        if (!libroRepository.existsById(id)) {
            throw new Exception("Libro no encontrado.");
        }
        libroRepository.deleteById(id);
    }
}
