package com.vianda_app.base.repositories;

import com.vianda_app.base.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    boolean existsByNombre(String nombre);

    Optional<Usuario> findByNombre(String nombre);
}
