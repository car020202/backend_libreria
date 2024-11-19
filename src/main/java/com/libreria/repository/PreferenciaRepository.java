package com.libreria.repository;

import com.libreria.model.Preferencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PreferenciaRepository extends JpaRepository<Preferencia, Long> {
    List<Preferencia> findByUsuarioId(Long userId);
    boolean existsByUsuarioIdAndLibroId(Long userId, Long libroId);
    Optional<Preferencia> findByUsuarioIdAndLibroId(Long userId, Long libroId);

}
