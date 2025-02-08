package com.vianda_app.base.controllers;

import com.vianda_app.base.dtos.LoginRequest;
import com.vianda_app.base.dtos.RegistroAdminRequest;
import com.vianda_app.base.dtos.RegistroClienteRequest;
import com.vianda_app.base.services.AuthService;
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

    @PostMapping("/register/cliente")
    public ResponseEntity<TokenResponse> register(@RequestBody final RegistroClienteRequest request) {
        final TokenResponse token = authService.registerUsuario(request);
        return ResponseEntity.ok(token);
    }

//    @PostMapping("/register/admin")
//    public ResponseEntity<TokenResponse> register(@RequestBody final RegistroAdminRequest request) {
//        final TokenResponse token = authService.registerUsuario(request);
//        return ResponseEntity.ok(token);
//    }

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
