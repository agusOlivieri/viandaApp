package com.vianda_app.base.repositories;

import com.vianda_app.base.entities.AdministradorDistribuidora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminDistribuidoraRepository extends JpaRepository<AdministradorDistribuidora, Integer> {
}
