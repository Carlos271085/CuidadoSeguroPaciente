package com.pacientes.pacientes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> manejarError(RuntimeException ex){

        Map<String, String> error = new HashMap<>();
        error.put("mensaje", ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<Map<String, String>> manejarValidaciones(
        MethodArgumentNotValidException ex){

    Map<String, String> errores = new HashMap<>();

    ex.getBindingResult().getFieldErrors().forEach(error ->
        errores.put(error.getField(), error.getDefaultMessage())
    );

    return ResponseEntity.badRequest().body(errores);
}    
}