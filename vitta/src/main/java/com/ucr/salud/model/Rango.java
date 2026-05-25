package com.ucr.salud.model;

import jakarta.persistence.*;

@Entity
@Table (name = "tb-rango")
public class Rango {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private Integer puntosMinimos;
    private Integer puntosMaximos;
    private String iconoUrl;
    private String colorHex;

    public Rango() {
    }

    public Rango(Integer id, String nombre, Integer puntosMinimos, Integer puntosMaximos, String iconoUrl, String colorHex) {
        this.id = id;
        this.nombre = nombre;
        this.puntosMinimos = puntosMinimos;
        this.puntosMaximos = puntosMaximos;
        this.iconoUrl = iconoUrl;
        this.colorHex = colorHex;
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

    public Integer getPuntosMinimos() {
        return puntosMinimos;
    }

    public void setPuntosMinimos(Integer puntosMinimos) {
        this.puntosMinimos = puntosMinimos;
    }

    public Integer getPuntosMaximos() {
        return puntosMaximos;
    }

    public void setPuntosMaximos(Integer puntosMaximos) {
        this.puntosMaximos = puntosMaximos;
    }

    public String getIconoUrl() {
        return iconoUrl;
    }

    public void setIconoUrl(String iconoUrl) {
        this.iconoUrl = iconoUrl;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }
}
