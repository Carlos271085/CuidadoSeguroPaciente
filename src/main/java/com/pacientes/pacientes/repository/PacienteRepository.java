package com.pacientes.pacientes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pacientes.pacientes.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    // Método para buscar pacientes por RUT
    List<Paciente> findByRut(String rut);
}