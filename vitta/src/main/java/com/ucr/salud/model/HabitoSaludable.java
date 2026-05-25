package com.ucr.salud.model;

import jakarta.persistence.*;

@Entity
@Table (name = "tb-habitoSaludable")
public class HabitoSaludable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer idRegistro;
    private String tipoHabito;
    private Boolean completado;
    private Integer puntosOtorgados;

    public HabitoSaludable() {
    }

    public HabitoSaludable(Integer id, Integer idRegistro, String tipoHabito, Boolean completado, Integer puntosOtorgados) {
        this.id = id;
        this.idRegistro = idRegistro;
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

    public Integer getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(Integer idRegistro) {
        this.idRegistro = idRegistro;
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
