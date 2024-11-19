package com.libreria.controllers;

import com.libreria.model.User;
import com.libreria.service.UserService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User newUser = userService.registerUser(user);
            return ResponseEntity.ok(newUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String password = request.get("password");
            User loggedInUser = userService.login(email, password);
            return ResponseEntity.ok(loggedInUser);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener los usuarios: " + e.getMessage());
        }
    }

    

    // Eliminar usuario (Solo admins)
    @DeleteMapping("/user/delete")
    public ResponseEntity<?> deleteUser(@RequestBody Map<String, Object> request) {
        try {
            Long id = Long.valueOf(request.get("id").toString());
            String adminEmail = request.get("adminEmail").toString();
            userService.deleteUser(id, adminEmail);
            return ResponseEntity.ok("Usuario eliminado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    // Editar usuario
    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestBody User updatedUser,
            @RequestParam String requesterEmail) {
        try {
            User user = userService.updateUser(id, updatedUser, requesterEmail);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
}
