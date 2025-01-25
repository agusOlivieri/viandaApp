package com.vianda_app.base.repositories;

import com.vianda_app.base.entities.ViandaDistribuidora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DistribuidoraRepository extends JpaRepository<ViandaDistribuidora, Integer> {
    Optional<ViandaDistribuidora> findByNombre(String nombre);
}
