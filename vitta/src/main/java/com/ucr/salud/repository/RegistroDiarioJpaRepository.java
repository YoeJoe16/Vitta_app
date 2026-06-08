package com.ucr.salud.repository;

import com.ucr.salud.model.RegistroDiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistroDiarioJpaRepository extends JpaRepository<RegistroDiario, Integer> {

List<RegistroDiario> findByUserId (Integer userId);

}
