package com.vianda_app.base.services;

import com.vianda_app.base.controllers.TokenResponse;
import com.vianda_app.base.dtos.RegistroRequest;
import com.vianda_app.base.entities.Rol;
import com.vianda_app.base.entities.Token;
import com.vianda_app.base.entities.Usuario;
import com.vianda_app.base.repositories.RolRepository;
import com.vianda_app.base.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioService usuarioService;

    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(TokenRepository tokenRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public TokenResponse register(RegistroRequest request) {
        Rol rol = rolRepository.findByNombre(request.getRol()).orElseThrow(() -> new RuntimeException("Rol no encontrado: " + request.getRol()));

        Usuario usuario = new Usuario(
                request.getUsername(),
                request.getApellido(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                rol
        );

        Usuario usuarioNuevo = usuarioService.save(usuario);
        var jwtToken = jwtService.generateToken(usuario);
        var refreshToken = jwtService.generateRefreshToken(usuario);

        saveUserToken(usuarioNuevo, jwtToken);
        return new TokenResponse(jwtToken, refreshToken);
    };

    private void saveUserToken(Usuario usuario, String jwtToken) {
        Token token = new Token(jwtToken, Token.TokenType.BEARER, false, false, usuario);

        tokenRepository.save(token);
    }
}
