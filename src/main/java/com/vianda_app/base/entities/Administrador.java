package com.vianda_app.base.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "administradores")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Administrador {

    @ManyToOne
    @JoinColumn(name = "distribuidora_id", nullable = false)
    private ViandaDistribuidora distribuidora;
}
