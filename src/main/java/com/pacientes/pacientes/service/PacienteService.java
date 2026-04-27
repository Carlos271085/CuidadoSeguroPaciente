package com.pacientes.pacientes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import com.pacientes.pacientes.model.Paciente;
import com.pacientes.pacientes.repository.PacienteRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import java.util.List;
import java.util.Map;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository repository;

    private final RestTemplate restTemplate = new RestTemplate();

    // URL dinámica según entorno (local o docker)
    @Value("${auth.url}")
    private String AUTH_URL;

    
    // VALIDAR TOKEN CON CIRCUIT BREAKER
    
@CircuitBreaker(name = "authService", fallbackMethod = "fallbackToken")
public boolean validarToken(String token) {
    try {
        String url = AUTH_URL + "?token=" + token;

        ResponseEntity<Map> response =
                restTemplate.getForEntity(url, Map.class);

        Map body = response.getBody();

        if (body == null) return false;

        Boolean valido = (Boolean) body.get("data");

        return Boolean.TRUE.equals(valido);

    } catch (Exception e) {
        System.out.println("Error al conectar con auth → usando fallback");
        return true;
    }
}
    
    // FALLBACK (cuando auth falla)
    
    public boolean fallbackToken(String token, Throwable t) {
        System.out.println("Auth no disponible → Circuit Breaker activado");
        return true; // permite continuar aunque auth esté caído
    }

    
    // GET
    
    public List<Paciente> obtenerTodos(String token) {

        if (!validarToken(token)) {
            throw new RuntimeException("No autorizado");
        }

        return repository.findAll();
    }

    
    // GET POR ID
    
    public Paciente obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
    }

    
    // POST
    
    public Paciente guardar(String token, Paciente paciente) {

        if (!validarToken(token)) {
            throw new RuntimeException("No autorizado");
        }

        return repository.save(paciente);
    }

    
    // PUT
    
    public Paciente actualizar(Long id, Paciente paciente) {

        Paciente existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        existente.setNombre(paciente.getNombre());
        existente.setEdad(paciente.getEdad());
        existente.setDiagnostico(paciente.getDiagnostico());

        return repository.save(existente);
    }

    // DELETE

    public void eliminar(Long id) {

        if (!repository.existsById(id)) {
            throw new RuntimeException("Paciente no encontrado");
        }

        repository.deleteById(id);
    }
}