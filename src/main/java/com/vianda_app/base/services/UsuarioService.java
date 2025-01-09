package com.vianda_app.base.services;

import com.vianda_app.base.entities.Usuario;
import com.vianda_app.base.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> getAll() { return usuarioRepository.findAll(); }

    public Usuario getById(Integer usuarioId) {
        return usuarioRepository.findById(usuarioId).orElseThrow(() -> new RuntimeException("Usuario no encontrado."));
    }

    public Usuario save(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    public boolean existsByNombre(String nombre) {
        return usuarioRepository.existsByNombre(nombre);
    }
}
