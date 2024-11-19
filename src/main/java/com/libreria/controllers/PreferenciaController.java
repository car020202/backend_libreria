package com.libreria.controllers;

import com.libreria.model.Preferencia;
import com.libreria.service.PreferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/preferencias")
public class PreferenciaController {

    @Autowired
    private PreferenciaService preferenciaService;

    @PostMapping
    public ResponseEntity<?> crearPreferencia(@RequestBody Map<String, Long> request) {
        try {
            Long userId = request.get("userId");
            Long libroId = request.get("libroId");
            Preferencia preferencia = preferenciaService.crearPreferencia(userId, libroId);
            return ResponseEntity.ok(preferencia);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> obtenerPreferencias(@PathVariable Long userId) {
        List<Preferencia> preferencias = preferenciaService.obtenerPreferenciasPorUsuario(userId);
        return ResponseEntity.ok(preferencias);
    }

    @DeleteMapping
    public ResponseEntity<?> eliminarPreferencia(@RequestBody Map<String, Long> request) {
        try {
            Long userId = request.get("userId");
            Long libroId = request.get("libroId");
            preferenciaService.eliminarPreferencia(userId, libroId);
            return ResponseEntity.ok("Preferencia eliminada.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
