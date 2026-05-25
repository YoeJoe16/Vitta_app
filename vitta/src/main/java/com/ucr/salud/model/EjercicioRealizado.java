package com.ucr.salud.model;

import jakarta.persistence.*;

@Entity
@Table (name = "tb-ejercicioRealizado")
public class EjercicioRealizado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer idRegistro;
    private Integer idTipoEjercicio;
    private Integer minutos;
    private String intensidad;
    private Integer puntosOtorgados;

    public EjercicioRealizado() {
    }

    public EjercicioRealizado(Integer id, Integer idRegistro, Integer idTipoEjercicio, Integer minutos, String intensidad, Integer puntosOtorgados) {
        this.id = id;
        this.idRegistro = idRegistro;
        this.idTipoEjercicio = idTipoEjercicio;
        this.minutos = minutos;
        this.intensidad = intensidad;
        this.puntosOtorgados = puntosOtorgados;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(Integer idRegistro) {
        this.idRegistro = idRegistro;
    }

    public Integer getIdTipoEjercicio() {
        return idTipoEjercicio;
    }

    public void setIdTipoEjercicio(Integer idTipoEjercicio) {
        this.idTipoEjercicio = idTipoEjercicio;
    }

    public Integer getMinutos() {
        return minutos;
    }

    public void setMinutos(Integer minutos) {
        this.minutos = minutos;
    }

    public String getIntensidad() {
        return intensidad;
    }

    public void setIntensidad(String intensidad) {
        this.intensidad = intensidad;
    }

    public Integer getPuntosOtorgados() {
        return puntosOtorgados;
    }

    public void setPuntosOtorgados(Integer puntosOtorgados) {
        this.puntosOtorgados = puntosOtorgados;
    }
}
