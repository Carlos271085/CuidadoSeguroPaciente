package com.pacientes.pacientes.service;

// Importaciones de Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// Importaciones para consumir APIs externas
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

// Importa modelo y repositorio
import com.pacientes.pacientes.model.Paciente;
import com.pacientes.pacientes.repository.PacienteRepository;

// Importa Circuit Breaker
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

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

        try {

            // Construye la URL con el token
            String url = AUTH_URL + "?token=" + token;

            // Realiza petición GET al microservicio auth
            ResponseEntity<Map> response =
                    restTemplate.getForEntity(url, Map.class);

            // Obtiene el body de la respuesta
            Map body = response.getBody();

            // Si la respuesta es nula retorna false
            if (body == null) return false;

            // Obtiene el valor booleano "data"
            Boolean valido = (Boolean) body.get("data");

            // Retorna true si el token es válido
            return Boolean.TRUE.equals(valido);

        } catch (Exception e) {

            // Mensaje de error si auth no responde
            System.out.println("Error al conectar con auth → usando fallback");

            return true;
        }
    }

    
    // FALLBACK CUANDO AUTH FALLA
    
    public boolean fallbackToken(String token, Throwable t) {

        System.out.println("Auth no disponible → Circuit Breaker activado");

        // Permite continuar aunque auth esté caído
        return true;
    }

    
    // GET TODOS LOS PACIENTES
    
    public List<Paciente> obtenerTodos(String token) {

        // Valida token antes de obtener datos
        if (!validarToken(token)) {
            throw new RuntimeException("No autorizado");
        }

        // Retorna lista completa de pacientes
        return repository.findAll();
    }

    
    // GET PACIENTE POR ID
    
    public Paciente obtenerPorId(Long id) {

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
    
    public Paciente actualizar(Long id, Paciente paciente) {

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
    
    public void eliminar(Long id) {

        // Verifica si el paciente existe
        if (!repository.existsById(id)) {
            throw new RuntimeException("Paciente no encontrado");
        }

        // Elimina paciente por ID
        repository.deleteById(id);
    }
}