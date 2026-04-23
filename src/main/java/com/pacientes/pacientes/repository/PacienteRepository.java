package com.pacientes.pacientes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pacientes.pacientes.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}