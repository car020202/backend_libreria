package com.libreria.service;

import com.libreria.model.Preferencia;
import com.libreria.model.Libro;
import com.libreria.model.User;
import com.libreria.repository.PreferenciaRepository;
import com.libreria.repository.LibroRepository;
import com.libreria.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreferenciaService {

    @Autowired
    private PreferenciaRepository preferenciaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LibroRepository libroRepository;

    public Preferencia crearPreferencia(Long userId, Long libroId) throws Exception {
        // Validar existencia de usuario y libro
        User usuario = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("Usuario no encontrado."));
        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new Exception("Libro no encontrado."));

        // Verificar duplicados
        if (preferenciaRepository.existsByUsuarioIdAndLibroId(userId, libroId)) {
            throw new Exception("El libro ya está en las preferencias del usuario.");
        }

        Preferencia preferencia = new Preferencia();
        preferencia.setUsuario(usuario);
        preferencia.setLibro(libro);
        preferencia.setFechaSeleccion(java.time.LocalDateTime.now());

        return preferenciaRepository.save(preferencia);
    }

    public List<Preferencia> obtenerPreferenciasPorUsuario(Long userId) {
        return preferenciaRepository.findByUsuarioId(userId);
    }

    public void eliminarPreferencia(Long userId, Long libroId) throws Exception {
        Preferencia preferencia = preferenciaRepository.findByUsuarioIdAndLibroId(userId, libroId)
                .orElseThrow(() -> new Exception("Preferencia no encontrada."));

        preferenciaRepository.delete(preferencia);
    }
}
