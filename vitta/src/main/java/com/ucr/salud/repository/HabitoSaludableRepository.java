package com.ucr.salud.repository;

import com.ucr.salud.model.HabitoSaludable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitoSaludableRepository extends JpaRepository<HabitoSaludable, Integer> {

    // Todos los habitos de un registro diario
    List<HabitoSaludable> findByIdUsuario(Integer idUsuario);

    // Solo los habitos completados de un registro
    List<HabitoSaludable> findByIdUsuarioAndCompletado(Integer idUsuario, Boolean completado);

    // Suma total de puntos otorgados en un registro
    @Query("SELECT COALESCE(SUM(h.puntosOtorgados), 0) FROM HabitoSaludable h WHERE h.idUsuario = :idUsuario")
    Integer sumPuntosByIdUsuario(@Param("idUsuario") Integer idUsuario);
}
