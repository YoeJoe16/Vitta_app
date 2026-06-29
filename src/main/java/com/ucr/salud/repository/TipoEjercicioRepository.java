package com.ucr.salud.repository;

import com.ucr.salud.model.TipoEjercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipoEjercicioRepository extends JpaRepository<TipoEjercicio, Integer> {

    List<TipoEjercicio> findByCategoria(String categoria);

    Optional<TipoEjercicio> findByNombre(String nombre);
}
