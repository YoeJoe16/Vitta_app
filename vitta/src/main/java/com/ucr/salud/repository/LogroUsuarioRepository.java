package com.ucr.salud.repository;

import com.ucr.salud.model.LogroUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LogroUsuarioRepository extends JpaRepository<LogroUsuario, Integer> {

    // Todos los logros obtenidos por un usuario
    List<LogroUsuario> findByIdUsuario(Integer idUsuario);

    // Verifica si un usuario ya tiene un logro especifico
    Optional<LogroUsuario> findByIdUsuarioAndIdLogro(Integer idUsuario, Integer idLogro);

    boolean existsByIdUsuarioAndIdLogro(Integer idUsuario, Integer idLogro);

    // Cuantos logros tiene un usuario (util para reportes)
    long countByIdUsuario(Integer idUsuario);
}
