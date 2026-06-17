package com.pacientes.pacientes.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class PacienteTest {

    @Test
    void deberiaCubrirMetodosGeneradosPorLombok() {

        Paciente paciente1 = new Paciente(
                1L,
                "20.123.456-7",
                "Juan",
                "Perez",
                LocalDate.of(1950, 1, 1),
                "Masculino",
                "Hipertension",
                "Penicilina",
                "Obs",
                "Direccion",
                "Viña",
                "123456",
                "correo@test.cl",
                "Hospital",
                "Tutor",
                "Hijo",
                "imagen.jpg"
        );

        Paciente paciente2 = new Paciente(
                1L,
                "20.123.456-7",
                "Juan",
                "Perez",
                LocalDate.of(1950, 1, 1),
                "Masculino",
                "Hipertension",
                "Penicilina",
                "Obs",
                "Direccion",
                "Viña",
                "123456",
                "correo@test.cl",
                "Hospital",
                "Tutor",
                "Hijo",
                "imagen.jpg"
        );

        assertEquals(paciente1, paciente2);
        assertEquals(paciente1.hashCode(), paciente2.hashCode());
        assertNotNull(paciente1.toString());
        assertEquals(1L, paciente1.getId());
    }
}