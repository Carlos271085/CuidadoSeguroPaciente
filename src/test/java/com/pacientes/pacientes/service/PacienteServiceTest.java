package com.pacientes.pacientes.service;

// Importaciones JUnit
import static org.junit.jupiter.api.Assertions.*;

// Importaciones Mockito
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

// Importaciones JUnit
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Importaciones Mockito
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

// Importa modelo y repositorio
import com.pacientes.pacientes.model.Paciente;
import com.pacientes.pacientes.repository.PacienteRepository;

public class PacienteServiceTest {

    // Simula el repositorio
    @Mock
    private PacienteRepository repository;

    // Inyecta el mock dentro del service
    @InjectMocks
    private PacienteService service;

    // Inicializa Mockito antes de cada test
    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
    }

    
    // TEST -> OBTENER TODOS LOS PACIENTES
    
    @Test
    void deberiaObtenerTodosLosPacientes() {

        // ARRANGE

        Paciente paciente = new Paciente();

        paciente.setId(1L);
        paciente.setRut("20.123.456-7");
        paciente.setNombre("Juan");
        paciente.setApellido("Pérez");
        paciente.setFechaNacimiento(LocalDate.of(1950, 5, 10));
        paciente.setGenero("Masculino");
        paciente.setDiagnostico("Hipertensión");

        // Simula respuesta del repositorio
        when(repository.findAll())
                .thenReturn(Arrays.asList(paciente));

        // ACT

        List<Paciente> resultado = service.obtenerTodos(null);

        // ASSERT

        assertEquals(1, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());
    }

    
    // TEST -> OBTENER PACIENTE POR ID
    
    @Test
    void deberiaObtenerPacientePorId() {

        // ARRANGE

        Paciente paciente = new Paciente();

        paciente.setId(1L);
        paciente.setNombre("María");

        // Simula búsqueda por ID
        when(repository.findById(1L))
                .thenReturn(Optional.of(paciente));

        // ACT

        Paciente resultado = service.obtenerPorId(1L);

        // ASSERT

        assertNotNull(resultado);
        assertEquals("María", resultado.getNombre());
    }

    
    // TEST -> GUARDAR PACIENTE
    
    @Test
    void deberiaGuardarPaciente() {

        // ARRANGE

        Paciente paciente = new Paciente();

        paciente.setNombre("Carlos");

        // Simula guardado en BD
        when(repository.save(paciente))
                .thenReturn(paciente);

        // ACT

        Paciente resultado = service.guardar(null, paciente);

        // ASSERT

        assertNotNull(resultado);
        assertEquals("Carlos", resultado.getNombre());
    }

    
    // TEST -> ELIMINAR PACIENTE
    
    @Test
    void deberiaEliminarPaciente() {

        // ARRANGE

        // Simula existencia del paciente
        when(repository.existsById(1L))
                .thenReturn(true);

        // ACT

        service.eliminar(1L);

        // ASSERT

        verify(repository, times(1))
                .deleteById(1L);
    }
}