package com.vianda_app.base.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "clientes")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Cliente extends Usuario{

    @ManyToOne
    @JoinColumn(name = "area_id", nullable = false)
    private Area area;

    public Cliente() {
        super();
    }

    public Cliente(String nombre, String apellido, String email, String password, Area area) {
        super(nombre, apellido, email, password);
        this.area = area;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
