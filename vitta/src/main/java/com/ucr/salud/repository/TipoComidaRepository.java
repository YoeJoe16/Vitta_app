package com.ucr.salud.repository;

import com.ucr.salud.model.TipoComida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipoComidaRepository extends JpaRepository<TipoComida, Integer> {

    List<TipoComida> findByCategoria(String categoria);

    List<TipoComida> findByNivelSaludable(String nivelSaludable);

    Optional<TipoComida> findByNombre(String nombre);
}
