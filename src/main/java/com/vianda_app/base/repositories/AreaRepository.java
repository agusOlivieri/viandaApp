package com.vianda_app.base.repositories;

import com.vianda_app.base.entities.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AreaRepository extends JpaRepository<Area, Integer> {
    Optional<Area> findByNombre(String nombre);
}
