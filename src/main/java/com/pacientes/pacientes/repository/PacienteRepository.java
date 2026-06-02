package com.pacientes.pacientes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pacientes.pacientes.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Optional<Paciente> findByRut(String rut);
    
    // Buscar paciente por RUT normalizado
    @Query("SELECT p FROM Paciente p WHERE REPLACE(REPLACE(p.rut, '.', ''), '-', '') = :rut")
    Optional<Paciente> findByRutNormalizado(@Param("rut") String rut);
}