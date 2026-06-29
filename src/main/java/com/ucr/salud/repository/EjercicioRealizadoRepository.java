package com.ucr.salud.repository;

import com.ucr.salud.model.EjercicioRealizado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EjercicioRealizadoRepository extends JpaRepository<EjercicioRealizado, Integer> {

    // Todos los ejercicios de un registro diario
    List<EjercicioRealizado> findByIdRegistro(Integer idRegistro);

    // Ejercicios filtrados por intensidad dentro de un registro
    List<EjercicioRealizado> findByIdRegistroAndIntensidad(Integer idRegistro, String intensidad);

    // Suma total de puntos otorgados en un registro
    @Query("SELECT COALESCE(SUM(e.puntosOtorgados), 0) FROM EjercicioRealizado e WHERE e.idRegistro = :idRegistro")
    Integer sumPuntosByIdRegistro(@Param("idRegistro") Integer idRegistro);

    // Total de minutos de ejercicio en un registro
    @Query("SELECT COALESCE(SUM(e.minutos), 0) FROM EjercicioRealizado e WHERE e.idRegistro = :idRegistro")
    Integer sumMinutosByIdRegistro(@Param("idRegistro") Integer idRegistro);
}
