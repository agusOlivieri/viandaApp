package com.vianda_app.base.repositories;

import com.vianda_app.base.entities.Vianda;
import com.vianda_app.base.entities.ViandaDistribuidora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViandaRepository extends JpaRepository<Vianda, Integer> {
    List<Vianda> findByDistribuidora(ViandaDistribuidora distribuidora);
}
