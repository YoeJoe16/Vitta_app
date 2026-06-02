package com.ucr.salud.repository;

import com.ucr.salud.model.RegistroDiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistroDiarioRepository extends JpaRepository<RegistroDiario, Integer> {

    // Todos los registros de un usuario ordenados por fecha descendente
    List<RegistroDiario> findByIdUsuarioOrderByFechaDesc(Integer idUsuario);

    // Alias usado en el service (sin orden)
    List<RegistroDiario> findByIdUsuario(Integer idUsuario);

    // Registro único por usuario y fecha (máximo uno por día)
    Optional<RegistroDiario> findByIdUsuarioAndFecha(Integer idUsuario, String fecha);
}
