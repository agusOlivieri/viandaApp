package com.vianda_app.base.controllers;

import com.vianda_app.base.dtos.RegistroRequest;
import com.vianda_app.base.entities.Rol;
import com.vianda_app.base.entities.RolNombre;
import com.vianda_app.base.entities.Usuario;
import com.vianda_app.base.repositories.RolRepository;
import com.vianda_app.base.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolRepository rolRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistroRequest registroRequest) {
        if(usuarioService.existsByNombre(registroRequest.getUsername())) {
            return ResponseEntity.badRequest().body("El nombre de usuario ya existe.");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(registroRequest.getUsername());
        usuario.setEmail(registroRequest.getEmail());
        usuario.setApellido(registroRequest.getApellido());

        Rol rol = rolRepository.findByNombre(RolNombre.valueOf(registroRequest.getRol()))
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + registroRequest.getRol()));

        usuario.setRol(rol);

        usuarioService.save(usuario);
        return ResponseEntity.ok("Usuario registrado exitosamente.");
    }

}
