package com.ucr.salud.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EjercicioRealizadoDTO {

    @NotNull(message = "El id del registro no puede ser nulo")
    private Integer idUsuario;

    @NotNull(message = "El id del tipo de ejercicio no puede ser nulo")
    private Integer idTipoEjercicio;

    @NotNull(message = "Los minutos no pueden ser nulos")
    @Min(value = 1, message = "Debe ser al menos 1 minuto")
    private Integer minutos;

    @NotBlank(message = "La intensidad no puede estar en blanco")
    private String intensidad;

    public EjercicioRealizadoDTO() {
    }

    public EjercicioRealizadoDTO(Integer idUsuario, Integer idTipoEjercicio, Integer minutos, String intensidad) {
        this.idUsuario = idUsuario;
        this.idTipoEjercicio = idTipoEjercicio;
        this.minutos = minutos;
        this.intensidad = intensidad;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
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
}
