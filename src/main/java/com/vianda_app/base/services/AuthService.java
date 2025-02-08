package com.vianda_app.base.services;

import com.vianda_app.base.controllers.TokenResponse;
import com.vianda_app.base.dtos.LoginRequest;
import com.vianda_app.base.dtos.RegistroAdminRequest;
import com.vianda_app.base.dtos.RegistroClienteRequest;
import com.vianda_app.base.entities.*;
import com.vianda_app.base.repositories.AreaRepository;
import com.vianda_app.base.repositories.DistribuidoraRepository;
import com.vianda_app.base.repositories.RolRepository;
import com.vianda_app.base.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private DistribuidoraRepository distribuidoraRepository;

    @Autowired
    private UsuarioService usuarioService;

    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(TokenRepository tokenRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public TokenResponse registerCliente(RegistroClienteRequest request) {
        Area area = areaRepository.findByNombre(request.getArea()).orElseThrow(() -> new RuntimeException("Área no encontrada: " + request.getArea()));

        Cliente cliente = new Cliente(
                request.getUsername(),
                request.getApellido(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                area
        );

        Cliente clienteNuevo = usuarioService.saveCliente(cliente);
        var jwtToken = jwtService.generateToken(cliente);
        var refreshToken = jwtService.generateRefreshToken(cliente);

        saveUserToken(clienteNuevo, jwtToken);
        return new TokenResponse(jwtToken, refreshToken);
    };

    public TokenResponse registerAdmin(RegistroAdminRequest request) {
        ViandaDistribuidora distribuidora = distribuidoraRepository.findByNombre(request.getDistribuidora()).orElseThrow(() -> new RuntimeException("Distribuidora no encontrada: " + request.getDistribuidora()));

        Administrador administrador = new Administrador(
                request.getUsername(),
                request.getApellido(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                distribuidora
        );

        Administrador adminNuevo = usuarioService.saveAdmin(administrador);
        var jwtToken = jwtService.generateToken(administrador);
        var refreshToken = jwtService.generateRefreshToken(administrador);

        saveUserToken(adminNuevo, jwtToken);
        return new TokenResponse(jwtToken, refreshToken);
    };

    public TokenResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            System.out.println("Error al autenticar: " + e.getMessage());
            throw e;
        }

        var usuario = usuarioService.getByNombre(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        var jwtToken = jwtService.generateToken(usuario);
        var refreshToken = jwtService.generateRefreshToken(usuario);
        revokeAllUserTokens(usuario);
        saveUserToken(usuario, jwtToken);

        return new TokenResponse(jwtToken, refreshToken);
    }

    private void saveUserToken(Usuario usuario, String jwtToken) {
        Token token = new Token(jwtToken, Token.TokenType.BEARER, false, false, usuario);

        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(final Usuario usuario) {
        final List<Token> validUserTokens = tokenRepository.findAllValidIsFalseOrRevokedIsFalseByUsuarioId(usuario.getId());

        if (!validUserTokens.isEmpty()) {
            for (final Token token : validUserTokens) {
                token.setExpired(true);
                token.setRevoked(true);
            }
            tokenRepository.saveAll(validUserTokens);
        }
    }

    public TokenResponse refreshToken(final String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Bearer Token.");
        }

        final String refreshToken = authHeader.substring(7);
        final String userNombre = jwtService.extractUsername(refreshToken);

        if (userNombre == null) {
            throw new IllegalArgumentException("Invalid Refresh Token.");
        }

        final Usuario usuario = usuarioService.getByNombre(userNombre)
                .orElseThrow(() -> new UsernameNotFoundException(userNombre));

        if (!jwtService.isTokenValid(refreshToken, usuario)) {
            throw new IllegalArgumentException("Invalid Refresh Token.");
        }

        final String accessToken = jwtService.generateToken(usuario);
        revokeAllUserTokens(usuario);
        saveUserToken(usuario, accessToken);
        return new TokenResponse(accessToken, refreshToken);
    }
}
