package com.ucr.salud.repository;

import com.ucr.salud.model.HabitoSaludable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitoSaludableRepository extends JpaRepository<HabitoSaludable, Integer> {

    // Todos los hábitos de un registro diario
    List<HabitoSaludable> findByIdRegistro(Integer idRegistro);

    // Solo los hábitos completados de un registro
    List<HabitoSaludable> findByIdRegistroAndCompletado(Integer idRegistro, Boolean completado);

    // Suma total de puntos otorgados en un registro
    @Query("SELECT COALESCE(SUM(h.puntosOtorgados), 0) FROM HabitoSaludable h WHERE h.idRegistro = :idRegistro")
    Integer sumPuntosByIdRegistro(@Param("idRegistro") Integer idRegistro);
}
