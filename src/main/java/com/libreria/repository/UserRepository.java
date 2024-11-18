package com.libreria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.libreria.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); // Validar si el email ya existe
}