package com.pacientes.pacientes.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pacientes")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El RUT es obligatorio")
    private String rut;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "El género es obligatorio")
    private String genero;

    @NotBlank(message = "El diagnóstico es obligatorio")
    private String diagnostico;

    private String alergias;

    private String observaciones;

    private String direccion;

    private String ciudad;

    private String telefono;

    @Email(message = "Debe ingresar un correo válido")
    private String email;

    private String centroMedico;

    private String tutorResponsable;

    private String parentescoTutor;

    private String imagenUrl;
}