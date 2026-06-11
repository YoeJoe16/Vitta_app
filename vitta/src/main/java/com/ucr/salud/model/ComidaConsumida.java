package com.ucr.salud.model;

import jakarta.persistence.*;

@Entity
@Table (name = "tb_comida_consumida")
public class ComidaConsumida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer idRegistro;
    private Integer idTipoComida;
    private Integer cantidadPorciones;
    private String momentoDelDia;
    private Integer puntosOtorgados;
    private Integer caloriasConsumidas;

    public ComidaConsumida() {
    }

    public ComidaConsumida(Integer id, Integer idRegistro, Integer idTipoComida, Integer cantidadPorciones, String momentoDelDia, Integer puntosOtorgados, Integer caloriasConsumidas) {
        this.id = id;
        this.idRegistro = idRegistro;
        this.idTipoComida = idTipoComida;
        this.cantidadPorciones = cantidadPorciones;
        this.momentoDelDia = momentoDelDia;
        this.puntosOtorgados = puntosOtorgados;
        this.caloriasConsumidas = caloriasConsumidas;
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

    public Integer getIdTipoComida() {
        return idTipoComida;
    }

    public void setIdTipoComida(Integer idTipoComida) {
        this.idTipoComida = idTipoComida;
    }

    public Integer getCantidadPorciones() {
        return cantidadPorciones;
    }

    public void setCantidadPorciones(Integer cantidadPorciones) {
        this.cantidadPorciones = cantidadPorciones;
    }

    public String getMomentoDelDia() {
        return momentoDelDia;
    }

    public void setMomentoDelDia(String momentoDelDia) {
        this.momentoDelDia = momentoDelDia;
    }

    public Integer getPuntosOtorgados() {
        return puntosOtorgados;
    }

    public void setPuntosOtorgados(Integer puntosOtorgados) {
        this.puntosOtorgados = puntosOtorgados;
    }

    public Integer getCaloriasConsumidas() {
        return caloriasConsumidas;
    }

    public void setCaloriasConsumidas(Integer caloriasConsumidas) {
        this.caloriasConsumidas = caloriasConsumidas;
    }
}
