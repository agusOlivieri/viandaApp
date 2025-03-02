package com.vianda_app.base.repositories;

import com.vianda_app.base.entities.AdministradorAguas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminAguasRepository extends JpaRepository<AdministradorAguas, Integer> {
}
