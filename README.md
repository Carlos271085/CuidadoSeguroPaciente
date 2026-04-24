#  Microservicio de Pacientes - Cuidado Seguro

##  Descripción

El microservicio de pacientes forma parte del sistema **Cuidado Seguro**, y tiene como objetivo gestionar la información de los pacientes, permitiendo realizar operaciones como registro, consulta, actualización y eliminación de datos clínicos.

Este microservicio está diseñado bajo una arquitectura de microservicios, permitiendo su integración con otros servicios como el microservicio de autenticación.


##  Tecnologías utilizadas

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 Database (base de datos en memoria)
- Maven
- Lombok
- Jakarta Validation

---

##  Arquitectura del Proyecto

El proyecto sigue una **arquitectura en capas**, separando responsabilidades para mejorar la mantenibilidad y escalabilidad.

controller → Manejo de endpoints REST
service → Lógica de negocio
repository → Acceso a datos (JPA)
model → Entidades del sistema
exception → Manejo global de errores


##  Estructura del proyecto
com.pacientes.pacientes
│
├── controller
│ └── PacienteController.java
│
├── service
│ └── PacienteService.java
│
├── repository
│ └── PacienteRepository.java
│
├── model
│ └── Paciente.java
│
├── exception
│ └── GlobalExceptionHandler.java
│
└── PacientesApplication.java


## Funcionalidades principales (CRUD)

El microservicio permite las siguientes operaciones:

| Método | Endpoint              | Descripción              |
|--------|----------------------|--------------------------|
| GET    | /pacientes           | Listar pacientes         |
| GET    | /pacientes/{id}      | Obtener paciente por ID  |
| POST   | /pacientes           | Crear paciente           |
| PUT    | /pacientes/{id}      | Actualizar paciente      |
| DELETE | /pacientes/{id}      | Eliminar paciente        |


##  Integración con Microservicio de Autenticación

El microservicio está preparado para integrarse con el servicio de autenticación mediante llamadas REST.

Antes de ejecutar operaciones, se valida el token a través del endpoint:

GET /api/auth/validate?token=XXX


Esto permite restringir el acceso solo a usuarios autenticados.

## Validaciones implementadas

Se utilizan anotaciones de Jakarta Validation para asegurar la integridad de los datos:

- `@NotBlank` → campos obligatorios
- `@Min` → validación de valores numéricos

Ejemplo:

```java
@NotBlank(message = "El nombre es obligatorio")
private String nombre;

Manejo de errores

Se implementa un manejador global de excepciones con:

@RestControllerAdvice

Permitiendo devolver respuestas claras como:

{
  "mensaje": "No autorizado"
}

Ejecución del proyecto
- Compilar
./mvnw clean install

- Ejecutar
./mvnw spring-boot:run

Ejemplo de petición (POST)
{
  "nombre": "Carlos",
  "edad": 25,
  "diagnostico": "Resfrío"
}

Patrones de diseño utilizados
Repository Pattern → acceso a datos
Arquitectura en capas → separación de responsabilidades
(Preparado) Factory Method → para creación de entidades


