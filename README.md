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

* **MySQL Server**
* **Spring Data JPA**
* **Hibernate ORM**

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

| Dependencia       | Descripción                                       |
| ----------------- | ------------------------------------------------- |
| Spring Web        | Creación de API REST                              |
| Spring Data JPA   | Persistencia de datos                             |
| Lombok            | Reducción de código repetitivo                    |
| MySQL Server      | Base de datos relacional utilizada por el sistema |
| Validation        | Validación de datos                               |
| Resilience4j      | Implementación de Circuit Breaker                 |
| Springdoc OpenAPI | Documentación Swagger                             |

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

# Configuración Base de Datos

```properties
# Nombre del microservicio
spring.application.name=pacientes

# Puerto del microservicio
server.port=8082

# URL del microservicio de autenticación
auth.url=http://localhost:9999/api/auth/validate

# Configuración Circuit Breaker
resilience4j.circuitbreaker.instances.authService.failureRateThreshold=50
resilience4j.circuitbreaker.instances.authService.slidingWindowSize=5
resilience4j.circuitbreaker.instances.authService.minimumNumberOfCalls=3
resilience4j.circuitbreaker.instances.authService.waitDurationInOpenState=10s
resilience4j.circuitbreaker.instances.authService.permittedNumberOfCallsInHalfOpenState=2

# CONFIGURACIÓN MYSQL

# URL de conexión a MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/pacientes

# Usuario MySQL
spring.datasource.username=root

# Contraseña MySQL
spring.datasource.password=

# Driver MySQL
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# CONFIGURACIÓN JPA / HIBERNATE

# Crea y actualiza tablas automáticamente
spring.jpa.hibernate.ddl-auto=update

# Mostrar consultas SQL en consola
spring.jpa.show-sql=true
```

## Explicación de la Configuración

| Configuración                 | Descripción                                 |
| ----------------------------- | ------------------------------------------- |
| spring.application.name       | Nombre del microservicio                    |
| server.port                   | Puerto de ejecución del microservicio       |
| auth.url                      | Endpoint del microservicio de autenticación |
| spring.datasource.url         | URL de conexión a MySQL                     |
| spring.datasource.username    | Usuario de la base de datos                 |
| spring.datasource.password    | Contraseña de MySQL                         |
| spring.jpa.hibernate.ddl-auto | Actualización automática de tablas          |
| spring.jpa.show-sql           | Muestra consultas SQL en consola            |
| resilience4j.circuitbreaker   | Configuración de tolerancia a fallos        |

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
      - SPRING_DATASOURCE_URL=jdbc:mysql://mysql-db:3306/cuidado_seguro
      - SPRING_DATASOURCE_USERNAME=root
      - SPRING_DATASOURCE_PASSWORD=1234
    depends_on:
      - mysql-db

  mysql-db:
    image: mysql:8
    container_name: mysql-pacientes
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: 1234
      MYSQL_DATABASE: cuidado_seguro
    ports:
      - "3307:3306"
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
  "rut": "20.123.456-7",
  "nombre": "Carlos",
  "apellido": "Bernal",
  "fechaNacimiento": "2004-05-10",
  "genero": "Masculino",
  "diagnostico": "Hipertensión",
  "alergias": "Penicilina",
  "observaciones": "Paciente estable",
  "direccion": "Villa Alemana",
  "ciudad": "Valparaíso",
  "telefono": "+56912345678",
  "email": "carlos@email.com",
  "controlMedico": "Mensual",
  "tutorResponsable": "María Bernal",
  "parentescoTutor": "Madre",
  "imagenUrl": "https://imagen.com/paciente.jpg"
}
```

---

# Ejemplo de Respuesta

```json
{
  "id": 1,
  "rut": "20.123.456-7",
  "nombre": "Carlos",
  "apellido": "Bernal",
  "fechaNacimiento": "2004-05-10",
  "genero": "Masculino",
  "diagnostico": "Hipertensión",
  "alergias": "Penicilina",
  "observaciones": "Paciente estable",
  "direccion": "Villa Alemana",
  "ciudad": "Valparaíso",
  "telefono": "+56912345678",
  "email": "carlos@email.com",
  "controlMedico": "Mensual",
  "tutorResponsable": "María Bernal",
  "parentescoTutor": "Madre",
  "imagenUrl": "https://imagen.com/paciente.jpg"
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
* MySQL Server instalado o Docker Desktop
* Puerto 8082 disponible
* Puerto 3306 o 3307 disponible para MySQL

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
