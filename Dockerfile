# ─── Etapa 1: Build ───────────────────────────────────────────────────────────
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Descargar dependencias (cacheado si pom.xml no cambia)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Compilar
COPY src ./src
RUN mvn clean package -DskipTests

# ─── Etapa 2: Runtime ─────────────────────────────────────────────────────────
FROM eclipse-temurin:17-jre-jammy

RUN groupadd -r appuser && useradd -r -g appuser appuser

WORKDIR /app

COPY --from=build /app/target/pacientes-0.0.1-SNAPSHOT.jar app.jar

RUN chown appuser:appuser app.jar
USER appuser

EXPOSE 8082

ENV JAVA_OPTS="-Xmx512m -Xms256m -XX:+UseG1GC -XX:+UseContainerSupport"

HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD wget -qO- http://localhost:8082/actuator/health || exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dspring.profiles.active=docker -jar app.jar"]
