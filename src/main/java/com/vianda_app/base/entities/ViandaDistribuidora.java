package com.vianda_app.base.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "distribuidoras")
public class ViandaDistribuidora {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "distribuidora_id")
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "distribuidora")
    private List<Vianda> viandas;

    public ViandaDistribuidora() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Vianda> getViandas() {
        return viandas;
    }

    public void setViandas(List<Vianda> viandas) {
        this.viandas = viandas;
    }
}
