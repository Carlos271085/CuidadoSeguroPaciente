package com.pacientes.pacientes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;  //  IMPORTANTE
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Min(value = 0, message = "La edad no puede ser negativa")
    private int edad;

    @NotBlank(message = "El diagnóstico es obligatorio")
    private String diagnostico;
}