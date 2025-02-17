package com.vianda_app.base.repositories;

import com.vianda_app.base.entities.Pedido;
import com.vianda_app.base.entities.Vianda;
import com.vianda_app.base.entities.ViandaDistribuidora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    @Query("SELECT p FROM Pedido p WHERE p.vianda.distribuidora.nombre = :nombreDistribuidora AND (p.fecha BETWEEN :inicioDia AND :finDia)")
    List<Pedido> findPedidosDelDiaByDistribuidora(@Param("nombreDistribuidora") String nombreDistribuidora, @Param("inicioDia")LocalDateTime inicioDia, @Param("finDia") LocalDateTime finDia);

    @Query("SELECT p FROM Pedido p WHERE YEAR(p.fecha) = :year AND MONTH(p.fecha) = :month")
    List<Pedido> findPedidosDelMes(@Param("year") int year, @Param("month") int month);
}
