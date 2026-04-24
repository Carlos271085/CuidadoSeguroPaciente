package com.pacientes.pacientes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import com.pacientes.pacientes.model.Paciente;
import com.pacientes.pacientes.repository.PacienteRepository;

import java.util.List;
import java.util.Map;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository repository;

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String AUTH_URL = "http://localhost:8080/api/auth/validate";

    // VALIDAR TOKEN
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
            return false;
        }
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