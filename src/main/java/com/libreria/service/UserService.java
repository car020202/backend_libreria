package com.libreria.service;

import com.libreria.model.User;
import com.libreria.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User registerUser(User user) throws Exception {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new Exception("El email ya está registrado.");
        }

        // Encriptar la clave
        user.setClave(passwordEncoder.encode(user.getClave()));
        user.setFechaRegistro(LocalDateTime.now());

        return userRepository.save(user);
    }

    public User login(String email, String password) throws Exception {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("Usuario no encontrado."));

        if (!passwordEncoder.matches(password, user.getClave())) {
            throw new Exception("Contraseña incorrecta.");
        }

        return user;
    }
}

