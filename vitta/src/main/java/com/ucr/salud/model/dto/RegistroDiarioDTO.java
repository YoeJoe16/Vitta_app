package com.ucr.salud.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RegistroDiarioDTO {

    @NotNull(message = "El id del usuario no puede ser nulo")
    private Integer idUsuario;

    @NotBlank(message = "La fecha no puede estar en blanco")
    private String fecha;

    @NotBlank(message = "La hora de registro no puede estar en blanco")
    private String horaRegistro;

    private String notas;

    public RegistroDiarioDTO() {
    }

    public RegistroDiarioDTO(Integer idUsuario, String fecha, String horaRegistro, String notas) {
        this.idUsuario = idUsuario;
        this.fecha = fecha;
        this.horaRegistro = horaRegistro;
        this.notas = notas;
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

    public String getHoraRegistro() {
        return horaRegistro;
    }

    public void setHoraRegistro(String horaRegistro) {
        this.horaRegistro = horaRegistro;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }
}
