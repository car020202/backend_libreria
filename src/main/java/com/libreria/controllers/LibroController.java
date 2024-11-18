package com.libreria.controllers;

import com.libreria.model.Libro;
import com.libreria.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @PostMapping("/create")
    public ResponseEntity<?> createLibro(@RequestBody Libro libro) {
        try {
            Libro newLibro = libroService.createLibro(libro);
            return ResponseEntity.ok(newLibro);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Libro>> getAllLibros() {
        return ResponseEntity.ok(libroService.getAllLibros());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateLibro(@PathVariable Long id, @RequestBody Libro libro) {
        try {
            Libro updatedLibro = libroService.updateLibro(id, libro);
            return ResponseEntity.ok(updatedLibro);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteLibro(@PathVariable Long id) {
        try {
            libroService.deleteLibro(id);
            return ResponseEntity.ok("Libro eliminado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
