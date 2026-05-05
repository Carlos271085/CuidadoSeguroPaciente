package com.pacientes.pacientes.model;

// Importa LocalDate para manejar fechas
import java.time.LocalDate;

// Importaciones de JPA
import jakarta.persistence.*;

// Importaciones para validaciones
import jakarta.validation.constraints.*;

// Importaciones de Lombok
import lombok.*;

// Marca esta clase como una entidad de la base de datos
@Entity

// Genera automáticamente getters, setters, toString, equals y hashCode
@Data

// Genera constructor vacío
@NoArgsConstructor

// Genera constructor con todos los atributos
@AllArgsConstructor
public class Paciente {

    // ID único del paciente
    @Id

    // Genera automáticamente el ID de forma incremental
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // RUT del paciente
    @NotBlank(message = "El RUT es obligatorio")
    private String rut;

    // Nombre del paciente
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    // Apellido del paciente
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    // Fecha de nacimiento del paciente
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNacimiento;

    // Género del paciente
    @NotBlank(message = "El género es obligatorio")
    private String genero;

    // Diagnóstico general del paciente
    @NotBlank(message = "El diagnóstico es obligatorio")
    private String diagnostico;

    // Alergias conocidas del paciente
    private String alergias;

    // Observaciones adicionales
    private String observaciones;

    // Dirección del paciente
    private String direccion;

    // Ciudad donde reside el paciente
    private String ciudad;

    // Número telefónico de contacto
    private String telefono;

    // Correo electrónico del paciente
    @Email(message = "Debe ingresar un correo válido")
    private String email;

    // Centro médico asociado al paciente
    private String centroMedico;

    // Nombre del tutor o familiar responsable
    private String tutorResponsable;

    // Relación del tutor con el paciente
    private String parentescoTutor;

    // URL o nombre de la imagen del paciente
    private String imagenUrl;
}