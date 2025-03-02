package com.vianda_app.base.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "administradores_distribuidoras")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class AdministradorDistribuidora extends Usuario{

    @ManyToOne
    @JoinColumn(name = "distribuidora_id", nullable = false)
    private ViandaDistribuidora distribuidora;

    public AdministradorDistribuidora(String nombre, String apellido, String email, String password, ViandaDistribuidora distribuidora) {
        super(nombre, apellido, email, password);
        this.distribuidora = distribuidora;
    }

    public AdministradorDistribuidora() {
        super();
    }

    public ViandaDistribuidora getDistribuidora() {
        return distribuidora;
    }

    public void setDistribuidora(ViandaDistribuidora distribuidora) {
        this.distribuidora = distribuidora;
    }
}
