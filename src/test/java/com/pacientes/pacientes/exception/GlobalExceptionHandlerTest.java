package com.pacientes.pacientes.exception;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GlobalExceptionHandlerTest {

    @Test
    void deberiaManejarRuntimeException() {

        GlobalExceptionHandler handler =
                new GlobalExceptionHandler();

        RuntimeException ex =
                new RuntimeException("No autorizado");

        ResponseEntity<Map<String, String>> response =
                handler.manejarError(ex);

        assertEquals(
                HttpStatus.UNAUTHORIZED,
                response.getStatusCode()
        );

        assertEquals(
                "No autorizado",
                response.getBody().get("mensaje")
        );
    }
}