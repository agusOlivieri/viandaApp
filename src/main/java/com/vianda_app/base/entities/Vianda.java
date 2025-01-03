package com.vianda_app.base.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "viandas")
public class Vianda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vianda_id")
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    private Integer precio;

    @ManyToOne
    @JoinColumn(name = "distribuidora_id")
    private ViandaDistribuidora distribuidora;

    public Vianda() {
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public ViandaDistribuidora getDistribuidora() {
        return distribuidora;
    }

    public void setDistribuidora(ViandaDistribuidora distribuidora) {
        this.distribuidora = distribuidora;
    }
}
