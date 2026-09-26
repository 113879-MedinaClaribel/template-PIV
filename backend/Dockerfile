# ==========================================
# Etapa 1: Compilación y empaquetado (Build)
# ==========================================
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /build

# Copiar archivos del Maven Wrapper y pom.xml primero para aprovechar el cache de capas de Docker
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Dar permisos de ejecución al wrapper
RUN chmod +x mvnw

# Descargar dependencias en una capa independiente (evita descargarlas si el código cambia pero el pom no)
RUN ./mvnw dependency:go-offline -B

# Copiar el código fuente y compilar el JAR
COPY src ./src
RUN ./mvnw clean package -DskipTests -B

# ==========================================
# Etapa 2: Imagen de ejecución mínima (Runtime)
# ==========================================
FROM eclipse-temurin:21-jre-alpine AS runner

WORKDIR /app

# Crear usuario sin privilegios por seguridad (principio de menor privilegio)
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Copiar el artefacto compilado desde la etapa anterior
COPY --from=builder /build/target/*.jar app.jar

# Variables de entorno por defecto
ENV PORT=8080
EXPOSE 8080

# Parámetros optimizados de la JVM para contenedores
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
