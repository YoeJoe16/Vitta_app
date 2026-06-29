package com.ucr.salud.repository;

import com.ucr.salud.model.Rango;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RangoRepository extends JpaRepository<Rango, Integer> {

    Optional<Rango> findByNombre(String nombre);

    // Encuentra el rango que corresponde a una cantidad de puntos
    @Query("SELECT r FROM Rango r WHERE :puntos >= r.puntosMinimos AND :puntos <= r.puntosMaximos")
    Optional<Rango> findByPuntos(@Param("puntos") Integer puntos);
}
