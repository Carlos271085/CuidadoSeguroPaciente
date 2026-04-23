package com.pacientes.pacientes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.pacientes.pacientes.model.Paciente;
import com.pacientes.pacientes.service.PacienteService;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService service;

    // GET -> listar pacientes
    @GetMapping
    public List<Paciente> listar(){
        return service.obtenerTodos();
    }

    // POST -> guardar paciente
    @PostMapping
    public Paciente guardar(@RequestBody Paciente paciente){
        return service.guardar(paciente);
    }
    
    
}
