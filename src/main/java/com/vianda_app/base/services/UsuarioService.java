package com.vianda_app.base.services;

import com.vianda_app.base.entities.AdministradorAguas;
import com.vianda_app.base.entities.AdministradorDistribuidora;
import com.vianda_app.base.entities.Cliente;
import com.vianda_app.base.entities.Usuario;
import com.vianda_app.base.repositories.AdminAguasRepository;
import com.vianda_app.base.repositories.AdminDistribuidoraRepository;
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
    private AdminDistribuidoraRepository adminDistribuidoraRepository;

    @Autowired
    private AdminAguasRepository adminAguasRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> getAll() { return usuarioRepository.findAll(); }

    public Usuario getById(Integer usuarioId) {
        return usuarioRepository.findById(usuarioId).orElseThrow(() -> new RuntimeException("Usuario no encontrado."));
    }

    public Cliente saveCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public AdministradorDistribuidora saveAdminDistribuidora(AdministradorDistribuidora admin) {
        return adminDistribuidoraRepository.save(admin);
    }

    public AdministradorAguas saveAdminAguas(AdministradorAguas admin) {
        return adminAguasRepository.save(admin);
    }

    public boolean existsByNombre(String nombre) {
        return usuarioRepository.existsByNombre(nombre);
    }

    public Optional<Usuario> getByNombre(String nombre) {
        return usuarioRepository.findByNombre(nombre);
    }
}
