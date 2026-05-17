# Microservicio de Pacientes - Cuidado Seguro

## Descripción

El microservicio de pacientes forma parte del sistema **Cuidado Seguro**, una plataforma orientada a la gestión y monitoreo de pacientes dentro de centros médicos y ELEAM.

Este microservicio tiene como objetivo administrar la información clínica de los pacientes mediante operaciones CRUD (Crear, Leer, Actualizar y Eliminar), permitiendo la integración con otros servicios del sistema bajo una arquitectura de microservicios.

Además, el servicio incorpora tolerancia a fallos mediante el patrón **Circuit Breaker** utilizando Resilience4j, permitiendo mantener la estabilidad del sistema ante posibles errores de comunicación con otros microservicios.

---

# Tecnologías Utilizadas

## Lenguaje y Framework

* **Java 17**
* **Spring Boot 3**
* **Spring Web**
* **Spring Data JPA**
* **Jakarta Validation**
* **Lombok**

## Base de Datos

* **H2 Database** (Base de datos en memoria)

## Documentación y Testing

* **Springdoc OpenAPI - Swagger UI**
* **Postman**

## Contenedores y DevOps

* **Docker**
* **Docker Compose**

## Resiliencia y Arquitectura

* **Resilience4j - Circuit Breaker**
* **Arquitectura de Microservicios**
* **Arquitectura en Capas**

---

# Arquitectura del Proyecto

El proyecto se encuentra organizado siguiendo una arquitectura en capas para mantener una correcta separación de responsabilidades.

## Capas implementadas

### Controller

Encargada de manejar las solicitudes HTTP y exponer los endpoints REST.

### Service

Contiene la lógica de negocio del sistema.

### Repository

Permite el acceso y manipulación de datos mediante Spring Data JPA.

### Model

Define las entidades utilizadas por el sistema.

### Exception

Maneja las excepciones globales y respuestas de error personalizadas.

---

# Estructura del Proyecto

```bash
com.pacientes.pacientes
│
├── controller
│   └── PacienteController.java
│
├── service
│   └── PacienteService.java
│
├── repository
│   └── PacienteRepository.java
│
├── model
│   └── Paciente.java
│
├── exception
│   └── GlobalExceptionHandler.java
│
└── PacientesApplication.java
```

---

# Dependencias Principales

| Dependencia       | Descripción                       |
| ----------------- | --------------------------------- |
| Spring Web        | Creación de API REST              |
| Spring Data JPA   | Persistencia de datos             |
| Lombok            | Reducción de código repetitivo    |
| H2 Database       | Base de datos en memoria          |
| Validation        | Validación de datos               |
| Resilience4j      | Implementación de Circuit Breaker |
| Springdoc OpenAPI | Documentación Swagger             |

---

# Instalación del Proyecto

## Clonar repositorio

```bash
git clone <URL_DEL_REPOSITORIO>
```

## Ingresar al proyecto

```bash
cd CuidadoSeguroPaciente
```

## Compilar proyecto

```bash
mvn clean package
```

---

# Ejecución del Proyecto

## Ejecución local

```bash
mvn spring-boot:run
```

## Ejecución con Docker

```bash
docker-compose up --build
```

---

# Configuración Docker

```yaml
services:
  pacientes-service:
    build: .
    container_name: pacientes-service
    ports:
      - "8082:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=docker
```

---

# Endpoints de la API

## Pacientes

| Método | Endpoint        | Descripción                 |
| ------ | --------------- | --------------------------- |
| GET    | /pacientes      | Obtener todos los pacientes |
| GET    | /pacientes/{id} | Obtener paciente por ID     |
| POST   | /pacientes      | Crear nuevo paciente        |
| PUT    | /pacientes/{id} | Actualizar paciente         |
| DELETE | /pacientes/{id} | Eliminar paciente           |

---

# Ejemplo de Petición POST

```json
{
  "nombre": "Carlos",
  "apellido": "Bernal",
  "edad": 21,
  "diagnostico": "Hipertensión",
  "alergias": "Penicilina"
}
```

---

# Ejemplo de Respuesta

```json
{
  "id": 1,
  "nombre": "Carlos",
  "apellido": "Bernal",
  "edad": 21,
  "diagnostico": "Hipertensión",
  "alergias": "Penicilina"
}
```

---

# Integración con Microservicio de Autenticación

El microservicio se encuentra preparado para integrarse con un servicio externo de autenticación mediante validación de tokens.

## URL de validación

```properties
auth.url=http://localhost:8080/api/auth/validate
```

---

# Implementación de Circuit Breaker

Se implementó el patrón **Circuit Breaker** utilizando **Resilience4j**.

## Objetivos

* Evitar la propagación de fallos entre microservicios
* Mantener la continuidad del sistema
* Implementar respuestas fallback ante errores externos
* Mejorar la estabilidad de la arquitectura distribuida

---

# Swagger - Documentación API

## Acceso Swagger UI

```bash
http://localhost:8082/swagger-ui.html
```

## Endpoint Base API

```bash
http://localhost:8082/pacientes
```

---

# Puertos Utilizados

| Puerto | Descripción                       |
| ------ | --------------------------------- |
| 8082   | Puerto expuesto del microservicio |
| 8080   | Puerto interno Spring Boot        |

---

# Pruebas del Sistema

Las pruebas de los endpoints pueden realizarse mediante:

* Swagger UI
* Postman
* Navegador web para peticiones GET

---

# Patrones de Diseño Implementados

## Repository Pattern

Permite desacoplar la lógica de acceso a datos.

## Arquitectura en Capas

Organiza el sistema separando responsabilidades.

## Circuit Breaker

Protege el sistema ante fallos de comunicación entre servicios.

---

# Requisitos Previos

Antes de ejecutar el proyecto se debe contar con:

* Java 17 instalado
* Maven instalado
* Docker Desktop instalado (opcional)
* Puerto 8082 disponible

---

# Autor

Proyecto desarrollado para la asignatura de Fullstack III.

Desarrollado por: Carlos Bernal.

---

# Conclusión

El microservicio de pacientes implementa una solución backend basada en Spring Boot siguiendo buenas prácticas de desarrollo backend moderno.

El sistema incorpora:

* Arquitectura de microservicios
* API REST
* Persistencia de datos
* Manejo de excepciones
* Tolerancia a fallos
* Contenedores Docker
* Documentación Swagger

Todo esto permite construir un servicio escalable, mantenible y preparado para integrarse con otros módulos del sistema Cuidado Seguro.
