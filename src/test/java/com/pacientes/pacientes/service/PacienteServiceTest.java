package com.pacientes.pacientes.service;

// Importaciones para las aserciones de JUnit
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
// Importaciones para Mockito
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
import org.mockito.Spy;

// Modelo y repositorio
import com.pacientes.pacientes.model.Paciente;
import com.pacientes.pacientes.repository.PacienteRepository;

public class PacienteServiceTest {

    /*
     * Simula el comportamiento del repositorio.
     * No se accede a una base de datos real.
     */
    @Mock
    private PacienteRepository repository;

    /*
     * Crea una instancia real de PacienteService,
     * pero permite modificar el comportamiento de algunos métodos.
     */
    @Spy
    @InjectMocks
    private PacienteService service;

    /*
     * Método que se ejecuta antes de cada prueba.
     * Inicializa los mocks de Mockito.
     */
    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        /*
         * Simula que la validación del token siempre es correcta.
         * Evita depender del microservicio Auth durante los tests.
         */
        doReturn(true)
                .when(service)
                .validarToken(any());
    }

    /*
     * Verifica que el servicio pueda obtener todos los pacientes.
     */
    @Test
    void deberiaObtenerTodosLosPacientes() {

        // ARRANGE

        // Crear paciente de prueba
        Paciente paciente = new Paciente();

        paciente.setId(1L);
        paciente.setRut("20.123.456-7");
        paciente.setNombre("Juan");
        paciente.setApellido("Pérez");
        paciente.setFechaNacimiento(LocalDate.of(1950, 5, 10));
        paciente.setGenero("Masculino");
        paciente.setDiagnostico("Hipertensión");

        /*
         * Simula que el repositorio devuelve una lista
         * con un único paciente.
         */
        when(repository.findAll())
                .thenReturn(Arrays.asList(paciente));

        // ACT

        // Ejecuta el método a probar
        List<Paciente> resultado = service.obtenerTodos("token");

        // ASSERT

        // Verifica que exista un paciente
        assertEquals(1, resultado.size());

        // Verifica el nombre del paciente
        assertEquals("Juan", resultado.get(0).getNombre());
    }

    /*
     * Verifica que el servicio obtenga un paciente por ID.
     */
    @Test
    void deberiaObtenerPacientePorId() {

        // ARRANGE

        Paciente paciente = new Paciente();

        paciente.setId(1L);
        paciente.setNombre("María");

        /*
         * Simula que el repositorio encuentra
         * el paciente solicitado.
         */
        when(repository.findById(1L))
                .thenReturn(Optional.of(paciente));

        // ACT

        Paciente resultado = service.obtenerPorId("token", 1L);

        // ASSERT

        // Verifica que el objeto no sea nulo
        assertNotNull(resultado);

        // Verifica el nombre esperado
        assertEquals("María", resultado.getNombre());
    }

    /*
     * Verifica que el servicio guarde correctamente
     * un nuevo paciente.
     */
    @Test
    void deberiaGuardarPaciente() {

        // ARRANGE

        Paciente paciente = new Paciente();

        paciente.setNombre("Carlos");

        /*
         * Simula el guardado exitoso
         * en la base de datos.
         */
        when(repository.save(paciente))
                .thenReturn(paciente);

        // ACT

        Paciente resultado = service.guardar("token", paciente);

        // ASSERT

        // Verifica que se haya retornado un objeto
        assertNotNull(resultado);

        // Verifica el nombre almacenado
        assertEquals("Carlos", resultado.getNombre());
    }

    /*
     * Verifica que el servicio elimine correctamente
     * un paciente existente.
     */
    @Test
    void deberiaEliminarPaciente() {

        // ARRANGE

        /*
         * Simula que el paciente existe
         * en la base de datos.
         */
        when(repository.existsById(1L))
                .thenReturn(true);

        // ACT

        service.eliminar("token", 1L);

        // ASSERT

        /*
         * Verifica que deleteById()
         * fue ejecutado exactamente una vez.
         */
        verify(repository, times(1))
                .deleteById(1L);
    }
    
    /*
 * Verifica que se lance una excepción
 * cuando el paciente no existe.
 */
@Test
void deberiaLanzarExcepcionCuandoPacienteNoExiste() {

    when(repository.findById(1L))
            .thenReturn(Optional.empty());

    assertThrows(
            RuntimeException.class,
            () -> service.obtenerPorId("token", 1L)
    );
}

/*
 * Verifica que se rechace un token inválido.
 */
@Test
void deberiaRechazarTokenInvalido() {

    doReturn(false)
            .when(service)
            .validarToken(any());

    assertThrows(
            RuntimeException.class,
            () -> service.obtenerTodos("token_invalido")
    );
}

/*
 * Verifica que no se pueda eliminar
 * un paciente inexistente.
 */
@Test
void noDeberiaEliminarPacienteInexistente() {

    when(repository.existsById(1L))
            .thenReturn(false);

    assertThrows(
            RuntimeException.class,
            () -> service.eliminar("token", 1L)
    );
}

@Test
void deberiaActualizarPaciente() {

    Paciente existente = new Paciente();
    existente.setId(1L);
    existente.setNombre("Juan");

    Paciente actualizado = new Paciente();
    actualizado.setNombre("Carlos");

    when(repository.findById(1L))
            .thenReturn(Optional.of(existente));

    when(repository.save(any(Paciente.class)))
            .thenReturn(existente);

    Paciente resultado =
            service.actualizar("token", 1L, actualizado);

    assertNotNull(resultado);
}

@Test
void deberiaBuscarPacientePorRut() {

    Paciente paciente = new Paciente();

    paciente.setRut("20.123.456-7");
    paciente.setNombre("Juan");

    when(repository.findByRut("20.123.456-7"))
            .thenReturn(Optional.of(paciente));

    Paciente resultado =
            service.buscarPorRut(
                    "token",
                    "20.123.456-7");

    assertNotNull(resultado);
    assertEquals("Juan", resultado.getNombre());
}

/* Actualizar() cuando el paciente no existe */
@Test
void deberiaLanzarExcepcionAlActualizarPacienteInexistente() {

    Paciente paciente = new Paciente();

    when(repository.findById(99L))
            .thenReturn(Optional.empty());

    assertThrows(
            RuntimeException.class,
            () -> service.actualizar(
                    "token",
                    99L,
                    paciente
            )
    );
}

/* Actualizar() con Token inválido */
@Test
void noDeberiaActualizarConTokenInvalido() {

    doReturn(false)
            .when(service)
            .validarToken(any());

    Paciente paciente = new Paciente();

    assertThrows(
            RuntimeException.class,
            () -> service.actualizar(
                    "token",
                    1L,
                    paciente
            )
    );
}

/* ObtenerPorId() con Token inválido */
@Test
void noDeberiaObtenerPacientePorIdConTokenInvalido() {

    doReturn(false)
            .when(service)
            .validarToken(any());

    assertThrows(
            RuntimeException.class,
            () -> service.obtenerPorId(
                    "token",
                    1L
            )
    );
}

/*Eliminar() con Token inválido */
@Test
void noDeberiaEliminarConTokenInvalido() {

    doReturn(false)
            .when(service)
            .validarToken(any());

    assertThrows(
            RuntimeException.class,
            () -> service.eliminar(
                    "token",
                    1L
            )
    );
}

/*BuscarPorRut() cuando no existe */
@Test
void deberiaLanzarExcepcionSiRutNoExiste() {

    when(repository.findByRut("11.111.111-1"))
            .thenReturn(Optional.empty());

    assertThrows(
            RuntimeException.class,
            () -> service.buscarPorRut(
                    "token",
                    "11.111.111-1"
            )
    );
}

/* FallbackToken() */
@Test
void deberiaRetornarTrueEnFallbackToken() {

    boolean resultado =
            service.fallbackToken(
                    "token",
                    new RuntimeException()
            );

    assertTrue(resultado);
}

}