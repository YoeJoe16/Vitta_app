package com.ucr.salud.model;

import jakarta.persistence.*;

@Entity
@Table (name = "tb-tipoComida")
public class TipoComida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String categoria;
    private Integer puntosBase;
    private String nivelSaludable;

    public TipoComida() {
    }

    public TipoComida(Integer id, String nombre, String categoria, Integer puntosBase, String nivelSaludable) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.puntosBase = puntosBase;
        this.nivelSaludable = nivelSaludable;
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getPuntosBase() {
        return puntosBase;
    }

    public void setPuntosBase(Integer puntosBase) {
        this.puntosBase = puntosBase;
    }

    public String getNivelSaludable() {
        return nivelSaludable;
    }

    public void setNivelSaludable(String nivelSaludable) {
        this.nivelSaludable = nivelSaludable;
    }
}
