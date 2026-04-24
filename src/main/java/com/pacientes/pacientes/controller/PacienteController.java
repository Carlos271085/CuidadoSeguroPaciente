package com.pacientes.pacientes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pacientes.pacientes.model.Paciente;
import com.pacientes.pacientes.service.PacienteService;

import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService service;

    // GET -> listar pacientes
    @GetMapping
    public ResponseEntity<List<Paciente>> listar(
            @RequestParam String token){

        return ResponseEntity.ok(service.obtenerTodos(token));
    }

    //Get por ID
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> obtenerPorID(
            @PathVariable Long id,
            @RequestParam String token){

        return ResponseEntity.ok(service.obtenerPorId(id));

        }

    // POST -> guardar paciente
    @PostMapping
    public ResponseEntity<Paciente> guardar(
        @RequestParam String token,
        @RequestBody Paciente paciente){

        return ResponseEntity.ok(service.guardar(token,paciente));

    
    }

    //Put
    @PutMapping("/{id}")
    public ResponseEntity<Paciente> actualizar(
        @PathVariable Long id,
        @RequestParam String token,
        @Valid @RequestBody Paciente paciente){

    return ResponseEntity.ok(service.actualizar(id, paciente));
}

    //Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(
        @PathVariable Long id,
        @RequestParam String token){

    service.eliminar(id);
    return ResponseEntity.ok("Paciente eliminado");
}

    
    
}
