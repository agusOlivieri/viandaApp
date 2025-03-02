package com.vianda_app.base.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "administradores_aguas")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class AdministradorAguas extends Usuario{

    public AdministradorAguas(String nombre, String apellido, String email, String password) {
        super(nombre, apellido, email, password);
    }

    public AdministradorAguas() {
        super();
    }
}
