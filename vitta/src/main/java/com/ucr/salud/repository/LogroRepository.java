package com.ucr.salud.repository;

import com.ucr.salud.model.Logro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LogroRepository extends JpaRepository<Logro, Integer> {

    Optional<Logro> findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}
