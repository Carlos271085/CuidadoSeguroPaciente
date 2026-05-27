#  Microservicio de Pacientes - Cuidado Seguro

---

##  Descripción

El microservicio de pacientes forma parte del sistema **Cuidado Seguro**, y tiene como objetivo gestionar la información clínica de los pacientes, permitiendo realizar operaciones CRUD (Crear, Leer, Actualizar y Eliminar).

Este servicio está diseñado bajo una arquitectura de microservicios, permitiendo su integración con otros servicios del sistema, como el microservicio de autenticación.

---

##  Tecnologías utilizadas

- Java 17  
- Spring Boot 3  
- Spring Web  
- Spring Data JPA  
- H2 Database (en memoria)  
- Maven  
- Lombok  
- Jakarta Validation  
- Docker  
- Resilience4j (Circuit Breaker)  
- Springdoc OpenAPI (Swagger)

---

##  Arquitectura del Proyecto

El proyecto sigue una arquitectura en capas:

- **Controller** → Manejo de endpoints REST  
- **Service** → Lógica de negocio  
- **Repository** → Acceso a datos (JPA)  
- **Model** → Entidades del sistema  
- **Exception** → Manejo global de errores  

---

##  Estructura del proyecto
com.pacientes.pacientes
│
├── controller
│ └── PacienteController.java
├── service
│ └── PacienteService.java
├── repository
│ └── PacienteRepository.java
├── model
│ └── Paciente.java
├── exception
│ └── GlobalExceptionHandler.java
└── PacientesApplication.java


---

##  Funcionalidades (CRUD)

| Método | Endpoint | Descripción |
|--------|--------|------------|
| GET | /pacientes | Listar pacientes |
| GET | /pacientes/{id} | Obtener paciente por ID |
| POST | /pacientes | Crear paciente |
| PUT | /pacientes/{id} | Actualizar paciente |
| DELETE | /pacientes/{id} | Eliminar paciente |

---

## Ejemplo de petición (POST)

```json
{
  "nombre": "Carlos",
  "edad": 25,
  "diagnostico": "Resfrío"
}

Integración con Microservicio de Autenticación

El microservicio está preparado para validar tokens mediante un servicio externo.

auth.url=http://localhost:8080/api/auth/validate

Patrón Circuit Breaker

Se implementa el patrón Circuit Breaker utilizando Resilience4j:

Permite continuidad del servicio
Evita caídas del sistema
Implementa método fallback

services:
  pacientes-service:
    build: .
    container_name: pacientes-service
    ports:
      - "8082:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=docker

Ejecución del proyecto
Compilar

mvn clean package

Ejecutar con Docker

docker-compose up --build

Acceso a la API
API:
http://localhost:8082/pacientes
Swagger UI:
http://localhost:8082/swagger-ui.html

Pruebas

Se pueden realizar pruebas utilizando:

Postman
Navegador (GET)
Swagger UI

Patrones de diseño utilizados
Repository Pattern
Arquitectura en capas
Circuit Breaker (Resilience4j)

Conclusión

El microservicio de pacientes cumple con los requerimientos de gestión de datos clínicos, implementando buenas prácticas de desarrollo como separación de responsabilidades, tolerancia a fallos y despliegue mediante contenedores Docker.



