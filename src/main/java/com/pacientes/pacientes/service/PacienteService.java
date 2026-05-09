package com.pacientes.pacientes.service;

// Importaciones de Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
// Importaciones para consumir APIs externas
import org.springframework.web.client.RestTemplate;

// Importa modelo y repositorio
import com.pacientes.pacientes.model.Paciente;
import com.pacientes.pacientes.repository.PacienteRepository;

// Importa Circuit Breaker
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import org.springframework.http.*;
// Importaciones Java
import java.util.List;
import java.util.Map;

// Marca esta clase como un servicio
@Service
public class PacienteService {

    // Inyecta el repositorio de pacientes
    @Autowired
    private PacienteRepository repository;

    // Permite consumir otros microservicios
    private final RestTemplate restTemplate = new RestTemplate();

    
    // Obtiene la URL del microservicio auth desde application.properties
    @Value("${auth.url}")
    private String AUTH_URL;

    
    // VALIDAR TOKEN CON CIRCUIT BREAKER
    
    @CircuitBreaker(name = "authService", fallbackMethod = "fallbackToken")
    public boolean validarToken(String token) {

        
        //System.out.println("Validando token: " + token + " en auth: " + url);

        try {

            HttpHeaders headers = new HttpHeaders();

            if (token != null && !token.isBlank()) {
                headers.setBearerAuth(token);
            }

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            String url = AUTH_URL;


            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            return response.getStatusCode() == HttpStatus.OK;

        } catch (HttpClientErrorException e) {

            System.out.println("Token no válido -> acceso denegado");
            return false;

        } catch (Exception e) {

            System.out.println("Error validando token: " + e.getMessage());
            return false;
        }
    }

    
    // FALLBACK CUANDO AUTH FALLA
    
    public boolean fallbackToken(String token, Throwable t) {

        //System.out.println("Auth no disponible -> Circuit Breaker activado");

        // Permite continuar aunque auth esté caído
        return true;
    }

    
    // GET TODOS LOS PACIENTES
    
    public List<Paciente> obtenerTodos(String token) {
        //System.out.println("Token: "+token);
        
        // Valida token antes de obtener datos
        if (!validarToken(token)) {
            throw new RuntimeException("No autorizado");
        }

        // Retorna lista completa de pacientes
        return repository.findAll();
    }

    
    // GET PACIENTE POR ID
    
    public Paciente obtenerPorId(Long id,String token) {
        if (!validarToken(token)) {
            throw new RuntimeException("No autorizado");
        }

        // Busca paciente por ID
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
    }

    
    // POST GUARDAR PACIENTE
    
    public Paciente guardar(String token, Paciente paciente) {

        // Valida token antes de guardar
        if (!validarToken(token)) {
            throw new RuntimeException("No autorizado");
        }

        // Guarda paciente en la base de datos
        return repository.save(paciente);
    }

    
    // PUT ACTUALIZAR PACIENTE
    
    public Paciente actualizar(Long id, Paciente paciente,String token) {

        if (!validarToken(token)) {
            throw new RuntimeException("No autorizado");
        }
        // Busca paciente existente
        Paciente existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        // Actualiza el RUT
        existente.setRut(paciente.getRut());

        // Actualiza nombre
        existente.setNombre(paciente.getNombre());

        // Actualiza apellido
        existente.setApellido(paciente.getApellido());

        // Actualiza fecha de nacimiento
        existente.setFechaNacimiento(paciente.getFechaNacimiento());

        // Actualiza género
        existente.setGenero(paciente.getGenero());

        // Actualiza diagnóstico
        existente.setDiagnostico(paciente.getDiagnostico());

        // Actualiza alergias
        existente.setAlergias(paciente.getAlergias());

        // Actualiza observaciones
        existente.setObservaciones(paciente.getObservaciones());

        // Actualiza dirección
        existente.setDireccion(paciente.getDireccion());

        // Actualiza ciudad
        existente.setCiudad(paciente.getCiudad());

        // Actualiza teléfono
        existente.setTelefono(paciente.getTelefono());

        // Actualiza email
        existente.setEmail(paciente.getEmail());

        // Actualiza centro médico
        existente.setCentroMedico(paciente.getCentroMedico());

        // Actualiza tutor responsable
        existente.setTutorResponsable(paciente.getTutorResponsable());

        // Actualiza parentesco del tutor
        existente.setParentescoTutor(paciente.getParentescoTutor());

        // Actualiza imagen del paciente
        existente.setImagenUrl(paciente.getImagenUrl());

        // Guarda cambios actualizados
        return repository.save(existente);
    }

    
    // DELETE ELIMINAR PACIENTE
    
    public void eliminar(Long id, String token) {

        if (!validarToken(token)) {
            throw new RuntimeException("No autorizado");
        }
        // Verifica si el paciente existe
        if (!repository.existsById(id)) {
            throw new RuntimeException("Paciente no encontrado");
        }

        // Elimina paciente por ID
        repository.deleteById(id);
    }
}