package com.pacientes.pacientes.controller;

// Importaciones de Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Importaciones Swagger/OpenAPI
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

// Importaciones del proyecto
import com.pacientes.pacientes.model.Paciente;
import com.pacientes.pacientes.service.PacienteService;

// Importaciones Java
import java.util.List;

// Importaciones para validaciones
import jakarta.validation.Valid;

// Personaliza el nombre del controlador en Swagger
@Tag(
    name = "Gestión de Pacientes",
    description = "Endpoints para administrar pacientes del sistema"
)

// Marca esta clase como controlador REST
@RestController

// Ruta base del controlador
@RequestMapping("/pacientes")
public class PacienteController {

    // Inyección del servicio de pacientes
    @Autowired
    private PacienteService service;

    
    // GET -> LISTAR TODOS LOS PACIENTES
    
    @Operation(summary = "Obtener todos los pacientes")

    @GetMapping
    public ResponseEntity<List<Paciente>> listar() {

        return ResponseEntity.ok(service.obtenerTodos(null));
    }

    
    // GET -> OBTENER PACIENTE POR ID
    
    @Operation(summary = "Obtener paciente por ID")

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> obtenerPorID(@PathVariable Long id) {

        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    
    // POST -> REGISTRAR PACIENTE
    
    @Operation(summary = "Registrar un nuevo paciente")

    @PostMapping
    public ResponseEntity<Paciente> guardar(@RequestBody Paciente paciente) {

        return ResponseEntity.ok(service.guardar(null, paciente));
    }

    
    // PUT -> ACTUALIZAR PACIENTE
    
    @Operation(summary = "Actualizar información de un paciente")

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Paciente paciente) {

        return ResponseEntity.ok(service.actualizar(id, paciente));
    }

    
    // DELETE -> ELIMINAR PACIENTE
    
    @Operation(summary = "Eliminar un paciente")

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {

        service.eliminar(id);

        return ResponseEntity.ok("Paciente eliminado");
    }
}