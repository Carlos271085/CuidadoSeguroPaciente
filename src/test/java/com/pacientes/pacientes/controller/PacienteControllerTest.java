package com.pacientes.pacientes.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import com.pacientes.pacientes.model.Paciente;
import com.pacientes.pacientes.service.PacienteService;

import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PacienteControllerTest {

    @Mock
    private PacienteService service;

    @InjectMocks
    private PacienteController controller;

    public PacienteControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deberiaListarPacientes() {

        Paciente paciente = new Paciente();

        paciente.setId(1L);
        paciente.setNombre("Juan");

        when(service.obtenerTodos("token"))
                .thenReturn(List.of(paciente));

        ResponseEntity<List<Paciente>> response =
                controller.listar("token");

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void deberiaObtenerPacientePorId() {

        Paciente paciente = new Paciente();

        paciente.setId(1L);
        paciente.setNombre("Juan");

        when(service.obtenerPorId("token", 1L))
                .thenReturn(paciente);

        ResponseEntity<Paciente> response =
                controller.obtenerPorID(1L, "token");

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Juan", response.getBody().getNombre());
    }

    @Test
    void deberiaGuardarPaciente() {

        Paciente paciente = new Paciente();

        paciente.setRut("123");
        paciente.setNombre("Carlos");
        paciente.setApellido("Bernal");
        paciente.setFechaNacimiento(LocalDate.now());

        when(service.guardar(any(), any()))
                .thenReturn(paciente);

        ResponseEntity<Paciente> response =
                controller.guardar(paciente, "token");

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void deberiaActualizarPaciente() {

        Paciente paciente = new Paciente();

        paciente.setNombre("Actualizado");

        when(service.actualizar(any(), any(), any()))
                .thenReturn(paciente);

        ResponseEntity<Paciente> response =
                controller.actualizar(
                        1L,
                        paciente,
                        "token"
                );

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void deberiaBuscarPorRut() {

        Paciente paciente = new Paciente();

        paciente.setRut("20.123.456-7");

        when(service.buscarPorRut(
                "token",
                "20.123.456-7"
        )).thenReturn(paciente);

        ResponseEntity<Paciente> response =
                controller.buscarPorRut(
                        "token",
                        "20.123.456-7"
                );

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void deberiaObtenerPorRut() {

        Paciente paciente = new Paciente();

        paciente.setRut("20.123.456-7");

        when(service.buscarPorRut(
                "token",
                "20.123.456-7"
        )).thenReturn(paciente);

        ResponseEntity<Paciente> response =
                controller.obtenerPorRut(
                        "20.123.456-7",
                        "token"
                );

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void deberiaEliminarPaciente() {

        ResponseEntity<String> response =
                controller.eliminar(
                        1L,
                        "token"
                );

        assertEquals(200, response.getStatusCode().value());
    }
}