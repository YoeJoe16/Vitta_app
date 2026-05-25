package com.ucr.salud.model;

import jakarta.persistence.*;

@Entity
@Table (name = "tb-registroDiario")
public class RegistroDiario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer idUsuario;
    private String fecha;
    private Integer puntosDelDia;
    private String notas;
    private String horaRegistro;

    public RegistroDiario() {
    }

    public RegistroDiario(String horaRegistro, String notas, Integer puntosDelDia, String fecha, Integer idUsuario, Integer id) {
        this.horaRegistro = horaRegistro;
        this.notas = notas;
        this.puntosDelDia = puntosDelDia;
        this.fecha = fecha;
        this.idUsuario = idUsuario;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getPuntosDelDia() {
        return puntosDelDia;
    }

    public void setPuntosDelDia(Integer puntosDelDia) {
        this.puntosDelDia = puntosDelDia;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getHoraRegistro() {
        return horaRegistro;
    }

    public void setHoraRegistro(String horaRegistro) {
        this.horaRegistro = horaRegistro;
    }
}
