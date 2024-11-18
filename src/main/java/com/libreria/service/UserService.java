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
    // Eliminar usuario (Solo admins)
    public void deleteUser(Long id, String adminEmail) throws Exception {
        User admin = userRepository.findByEmail(adminEmail)
                .orElseThrow(() -> new Exception("Admin no encontrado."));

        if (!admin.getAccesoSistema()) {
            throw new Exception("Acceso denegado. Solo los admins pueden eliminar usuarios.");
        }

        User userToDelete = userRepository.findById(id)
                .orElseThrow(() -> new Exception("Usuario no encontrado."));

        userRepository.delete(userToDelete);
    }

   
   // Editar usuario
    public User updateUser(Long id, User updatedUser, String requesterEmail) throws Exception {
    // Obtener el usuario que está realizando la solicitud
     User requester = userRepository.findByEmail(requesterEmail)
            .orElseThrow(() -> new Exception("Usuario solicitante no encontrado."));

     // Obtener el usuario a editar
     User existingUser = userRepository.findById(id)
            .orElseThrow(() -> new Exception("Usuario no encontrado."));

     // Validar permisos: un usuario solo puede editar su propio perfil
        if (!requester.getAccesoSistema() && !requester.getId().equals(id)) {
        throw new Exception("Acceso denegado. No puedes editar este usuario.");
        }

        // Si es un usuario normal, asegurarse de que no pueda modificar 'estado' ni 'acceso_sistema'
        if (!requester.getAccesoSistema()) {
        updatedUser.setAccesoSistema(existingUser.getAccesoSistema()); // Mantener acceso original
        updatedUser.setEstado(existingUser.getEstado()); // Mantener estado original
        }

     // Si es un administrador, podrá modificar todos los campos excepto 'estado' y 'acceso_sistema' en su propio perfil
    if (requester.getAccesoSistema()) {
        // Si el administrador está editando su propio perfil, puede modificar todo
        if (requester.getId().equals(id)) {
            updatedUser.setAccesoSistema(existingUser.getAccesoSistema());
            updatedUser.setEstado(existingUser.getEstado());
        } else {
            // Si el administrador está editando otro usuario, solo puede cambiar 'nombre', 'apellido', 'email', 'clave'
            updatedUser.setAccesoSistema(existingUser.getAccesoSistema()); // Mantener acceso original
            updatedUser.setEstado(existingUser.getEstado()); // Mantener estado original
        }
    }

    // Encriptar contraseña si fue actualizada
    if (updatedUser.getClave() != null && !updatedUser.getClave().isEmpty()) {
        updatedUser.setClave(passwordEncoder.encode(updatedUser.getClave()));
    } else {
        updatedUser.setClave(existingUser.getClave()); // Mantener contraseña existente
    }

    // Actualizar los demás campos
        existingUser.setNombre(updatedUser.getNombre());
        existingUser.setApellido(updatedUser.getApellido());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setClave(updatedUser.getClave());
        existingUser.setAccesoSistema(updatedUser.getAccesoSistema());
        existingUser.setEstado(updatedUser.getEstado());

     return userRepository.save(existingUser);
    }
}

