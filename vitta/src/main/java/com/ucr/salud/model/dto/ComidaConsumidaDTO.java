package com.ucr.salud.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ComidaConsumidaDTO {

    @NotNull(message = "El id del registro no puede ser nulo")
    private Integer idRegistro;

    @NotNull(message = "El id del tipo de comida no puede ser nulo")
    private Integer idTipoComida;

    @NotNull(message = "La cantidad de porciones no puede ser nula")
    @Min(value = 1, message = "Debe ser al menos 1 porción")
    private Integer cantidadPorciones;

    @NotBlank(message = "El momento del día no puede estar en blanco")
    private String momentoDelDia;

    public ComidaConsumidaDTO() {
    }

    public ComidaConsumidaDTO(Integer idRegistro, Integer idTipoComida, Integer cantidadPorciones, String momentoDelDia) {
        this.idRegistro = idRegistro;
        this.idTipoComida = idTipoComida;
        this.cantidadPorciones = cantidadPorciones;
        this.momentoDelDia = momentoDelDia;
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
}
