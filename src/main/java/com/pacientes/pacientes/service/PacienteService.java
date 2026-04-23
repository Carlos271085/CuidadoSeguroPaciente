package com.pacientes.pacientes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pacientes.pacientes.model.Paciente;
import com.pacientes.pacientes.repository.PacienteRepository;

import java.util.List;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository repository;

    public List<Paciente> obtenerTodos() {
        return repository.findAll();
    }

    public Paciente guardar(Paciente paciente){
        return repository.save(paciente);
    }
}