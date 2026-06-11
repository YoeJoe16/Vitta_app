package com.ucr.salud.model;

import jakarta.persistence.*;

@Entity
@Table (name = "tb-tipoEjercicio")
public class TipoEjercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String categoria;
    private Integer puntosPorMinuto;
    private String descripcion;
    private Integer idTipoEjercicio;

    public TipoEjercicio() {
    }

    public TipoEjercicio(Integer id, String nombre, String categoria, Integer puntosPorMinuto, String descripcion, Integer idTipoEjercicio) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.puntosPorMinuto = puntosPorMinuto;
        this.descripcion = descripcion;
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

    public Integer getPuntosPorMinuto() {
        return puntosPorMinuto;
    }

    public void setPuntosPorMinuto(Integer puntosPorMinuto) {
        this.puntosPorMinuto = puntosPorMinuto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIdTipoEjercicio() {
        return idTipoEjercicio;
    }

    public void setIdTipoEjercicio(Integer idTipoEjercicio) {
        this.idTipoEjercicio = idTipoEjercicio;
    }
}
