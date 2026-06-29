package com.ucr.salud.model;

import jakarta.persistence.*;

@Entity
@Table (name = "tb_habito_saludable")
public class HabitoSaludable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer idUsuario;
    private String tipoHabito;
    private Boolean completado;
    private Integer puntosOtorgados;

    public HabitoSaludable() {
    }

    public HabitoSaludable(Integer id, Integer idUsuario, String tipoHabito, Boolean completado, Integer puntosOtorgados) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.tipoHabito = tipoHabito;
        this.completado = completado;
        this.puntosOtorgados = puntosOtorgados;
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

    public String getTipoHabito() {
        return tipoHabito;
    }

    public void setTipoHabito(String tipoHabito) {
        this.tipoHabito = tipoHabito;
    }

    public Boolean getCompletado() {
        return completado;
    }

    public void setCompletado(Boolean completado) {
        this.completado = completado;
    }

    public Integer getPuntosOtorgados() {
        return puntosOtorgados;
    }

    public void setPuntosOtorgados(Integer puntosOtorgados) {
        this.puntosOtorgados = puntosOtorgados;
    }
}
