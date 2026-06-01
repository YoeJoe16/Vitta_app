package com.ucr.salud.repository;

import com.ucr.salud.model.ComidaConsumida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComidaConsumidaRepository extends JpaRepository<ComidaConsumida, Integer> {

    // Todas las comidas de un registro diario
    List<ComidaConsumida> findByIdRegistro(Integer idRegistro);

    // Comidas de un registro filtradas por momento del día
    List<ComidaConsumida> findByIdRegistroAndMomentoDelDia(Integer idRegistro, String momentoDelDia);

    // Suma total de puntos otorgados en un registro (usado para recalcularPuntos)
    @Query("SELECT COALESCE(SUM(c.puntosOtorgados), 0) FROM ComidaConsumida c WHERE c.idRegistro = :idRegistro")
    Integer sumPuntosByIdRegistro(@Param("idRegistro") Integer idRegistro);
}
