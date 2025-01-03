package com.vianda_app.base.repositories;

import com.vianda_app.base.entities.Vianda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViandaRepository extends JpaRepository<Vianda, Integer> {

}
