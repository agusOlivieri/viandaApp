package com.vianda_app.base.services;

import com.vianda_app.base.entities.Usuario;
import com.vianda_app.base.repositories.AdministradorRepository;
import com.vianda_app.base.repositories.ClienteRepository;
import com.vianda_app.base.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> getAll() { return usuarioRepository.findAll(); }

    public Usuario getById(Integer usuarioId) {
        return usuarioRepository.findById(usuarioId).orElseThrow(() -> new RuntimeException("Usuario no encontrado."));
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public boolean existsByNombre(String nombre) {
        return usuarioRepository.existsByNombre(nombre);
    }

    public Optional<Usuario> getByNombre(String nombre) {
        return usuarioRepository.findByNombre(nombre);
    }
}
