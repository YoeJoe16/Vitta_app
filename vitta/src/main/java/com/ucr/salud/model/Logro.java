package com.ucr.salud.model;

import jakarta.persistence.*;

@Entity
@Table (name = "tb-logro")
public class Logro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String descripcion;
    private String condicion;
    private Integer puntosBonus;
    private String iconoUrl;
    private Integer idLogro;

    public Logro() {
    }

    public Logro(Integer id, String nombre, String descripcion, String condicion, Integer puntosBonus, String iconoUrl, Integer idLogro) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.condicion = condicion;
        this.puntosBonus = puntosBonus;
        this.iconoUrl = iconoUrl;
        this.idLogro= idLogro;
    }

    public Integer getId() {
        return id;
    }

    public void setIdLogro(Integer idLogro) {
        this.idLogro = idLogro;
    }

    public Integer getIdLogro() {
        return idLogro;
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

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public Integer getPuntosBonus() {
        return puntosBonus;
    }

    public void setPuntosBonus(Integer puntosBonus) {
        this.puntosBonus = puntosBonus;
    }

    public String getIconoUrl() {
        return iconoUrl;
    }

    public void setIconoUrl(String iconoUrl) {
        this.iconoUrl = iconoUrl;
    }


}
