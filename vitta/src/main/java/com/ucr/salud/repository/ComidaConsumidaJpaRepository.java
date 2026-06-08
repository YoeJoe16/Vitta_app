package com.ucr.salud.repository;

import com.ucr.salud.model.ComidaConsumida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComidaConsumidaJpaRepository extends JpaRepository<ComidaConsumida, Integer> {

}
