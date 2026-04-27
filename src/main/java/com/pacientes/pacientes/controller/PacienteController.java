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
    public ResponseEntity<List<Paciente>> listar() {
        return ResponseEntity.ok(service.obtenerTodos(null));
    }

    // GET por ID
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> obtenerPorID(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    // POST -> guardar paciente
    @PostMapping
    public ResponseEntity<Paciente> guardar(@RequestBody Paciente paciente) {
        return ResponseEntity.ok(service.guardar(null, paciente));
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<Paciente> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Paciente paciente) {

        return ResponseEntity.ok(service.actualizar(id, paciente));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {

        service.eliminar(id);
        return ResponseEntity.ok("Paciente eliminado");
    }
}