// UserController.java
package com.microrecetas.usuarios.controller;

import com.microrecetas.usuarios.jwt.JWTAuthtenticationConfig;
import com.microrecetas.usuarios.model.Receta;
import com.microrecetas.usuarios.model.User;
import com.microrecetas.usuarios.service.CustomUserDetailsService;
import com.microrecetas.usuarios.service.RecetaService;
import com.microrecetas.usuarios.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    JWTAuthtenticationConfig jwtAuthtenticationConfig;

     @Autowired
    private PasswordEncoder passwordEncoder;  // Añade esta línea

   @Autowired
    private RecetaService recetaService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(
            @RequestParam(required = true) String username,
            @RequestParam(required = true) String password) {
        try {
            // Log para debugging
            System.out.println("Login attempt for user: " + username);
            
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            
            // Log para debugging
            System.out.println("User found in database");
            
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                System.out.println("Password doesn't match");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials");
            }

            String token = jwtAuthtenticationConfig.getJWTToken(username);
            return ResponseEntity.ok(token);
            
        } catch (UsernameNotFoundException e) {
            System.out.println("User not found: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("User not found");
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred");
        }
    }

    @GetMapping("/validation")
    public ResponseEntity<String> validateUser() {
        // Obtén la información de autenticación del contexto de seguridad
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Verifica si el usuario está autenticado
        if (authentication != null && authentication.isAuthenticated()) {
            String token = jwtAuthtenticationConfig.getJWTToken(authentication.getName());
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
        }
    }

    // Endpoint público para obtener solo los nombres de las recetas
    @GetMapping("/recetas/nombres")
    public ResponseEntity<List<String>> getNombresRecetas() {
        List<String> nombresRecetas = recetaService.obtenerNombresDeRecetas();
        return ResponseEntity.ok(nombresRecetas);
    }

    // Nuevo endpoint público para insertar múltiples recetas
    @PostMapping("/recetas/multiples")
    public ResponseEntity<String> insertarMultiplesRecetas(@RequestBody List<Receta> recetas) {
        recetaService.guardarRecetas(recetas);
        return ResponseEntity.status(HttpStatus.CREATED).body("Recetas insertadas exitosamente");
    }
}
