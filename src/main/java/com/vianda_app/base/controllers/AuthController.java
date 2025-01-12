package com.vianda_app.base.controllers;

import com.vianda_app.base.dtos.LoginRequest;
import com.vianda_app.base.dtos.RegistroRequest;
import com.vianda_app.base.entities.Rol;
import com.vianda_app.base.entities.Token;
import com.vianda_app.base.entities.Usuario;
import com.vianda_app.base.repositories.RolRepository;
import com.vianda_app.base.repositories.TokenRepository;
import com.vianda_app.base.services.AuthService;
import com.vianda_app.base.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody final RegistroRequest request) {
        final TokenResponse token = authService.register(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> authenticate(@RequestBody final LoginRequest request) {
        final TokenResponse token = authService.login(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    public TokenResponse refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader) {
        return authService.refreshToken(authHeader);
    }
}
