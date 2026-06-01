package com.ucr.salud.model;

import jakarta.persistence.*;

@Entity
@Table (name = "tb-logroUsuario")
public class LogroUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer idUsuario;
    private Integer idLogro;
    private String fechaObtenido;

    public LogroUsuario() {
    }

    public LogroUsuario(Integer id, Integer idUsuario, Integer idLogro, String fechaObtenido) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idLogro = idLogro;
        this.fechaObtenido = fechaObtenido;
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

    public Integer getIdLogro() {
        return idLogro;
    }

    public void setIdLogro(Integer idLogro) {
        this.idLogro = idLogro;
    }

    public String getFechaObtenido() {
        return fechaObtenido;
    }

    public void setFechaObtenido(String fechaObtenido) {
        this.fechaObtenido = fechaObtenido;
    }
}
