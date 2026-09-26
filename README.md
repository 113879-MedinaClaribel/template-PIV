# Fullstack Template Base: Java 21 + Maven + Angular + Tailwind + Docker

Plantilla base enterprise pensada bajo los principios de **Clean Architecture**, **Screaming Architecture**, componentes **Standalone** con reactividad mediante **Signals**, y orquestación con **Docker Multi-Stage**.

---

## 🏛️ Filosofía Arquitectónica: Conceptos antes que Código

Este repositorio no es un rejunte de carpetas: cada decisión técnica responde a principios de ingeniería sólidos:

1. **Backend desacoplado de la persistencia**: La base de datos es un *detalle de implementación* (Clean Architecture). El núcleo de la aplicación define interfaces y contratos (DTOs con Java Records inmutables). Podés conectar PostgreSQL, MongoDB o MySQL agregando el starter correspondiente sin tocar la lógica de negocio.
2. **Frontend Standalone & Reactivo**: Se eliminaron los antiguos `NgModules`. Angular 20 opera con componentes Standalone, inyección funcional (`inject()`) y reactividad granular con **Signals**, reduciendo drásticamente la sobrecarga de la detección de cambios de Zone.js.
3. **Reproducibilidad Garantizada (Maven Wrapper)**: No dependas de instalaciones globales de Maven en tu sistema. El script `./mvnw` (Linux/macOS) o `mvnw.cmd` (Windows) asegura que cualquier desarrollador o servidor de CI compile exactamente con la misma versión.
4. **Contenedores de Producción Multi-Stage**: Separamos estrictamente el entorno de *build* (con compiladores pesados) del entorno de *runtime* (imágenes Alpine mínimas y seguras con usuarios sin privilegios `root`).

---

## 📂 Estructura del Proyecto

```text
Repo-base/
├── backend/                       # API REST Spring Boot 3.4.x con Java 21
│   ├── mvnw / mvnw.cmd            # Maven Wrapper
│   ├── pom.xml                    # Dependencias y plugins de Maven
│   ├── Dockerfile                 # Multi-stage: JDK 21 Alpine -> JRE 21 Alpine
│   └── src/
│       ├── main/
│       │   ├── java/com/example/template/
│       │   │   ├── config/        # Configuración de CORS, OpenAPI y beans globales
│       │   │   ├── controllers/   # Puerta HTTP: validación y enrutamiento
│       │   │   ├── services/      # Lógica de negocio (Interfaces e Impl con @Transactional)
│       │   │   ├── dtos/          # Contratos inmutables (Java 21 Records)
│       │   │   ├── entities/      # Entidades JPA (@Entity, @Table)
│       │   │   ├── repositories/  # Spring Data JPA Repositories (JpaRepository)
│       │   │   ├── exceptions/    # BusinessException y GlobalExceptionHandler
│       │   │   └── BackendApplication.java
│       │   └── resources/
│       │       └── application.yml# Configuración H2/PostgreSQL y Virtual Threads
│       └── test/                  # Tests unitarios e integración (JUnit 5)
│
├── frontend/                      # SPA Angular 20 + Tailwind CSS
│   ├── Dockerfile                 # Multi-stage: Node 22 Alpine -> Nginx Alpine
│   ├── nginx.conf                 # Configuración de servidor Nginx y rutas SPA
│   ├── tailwind.config.js         # Configuración de Tailwind CSS
│   └── src/
│       └── app/
│           ├── core/              # Servicios API singleton e interceptores
│           ├── shared/            # Componentes presentacionales reutilizables
│           ├── features/          # Módulos y vistas por dominio
│           ├── app.config.ts      # Proveedores centrales (Router, HttpClient)
│           └── app.routes.ts      # Rutas lazy-loaded
│
├── docker-compose.yml             # Orquestación de contenedores locales
├── .dockerignore                  # Exclusiones de contexto para Docker
├── .gitignore                     # Exclusiones de Git
└── README.md                      # Documentación del proyecto
```

---

## 🚀 Requisitos Previos

* **Java 21 JDK**: Para desarrollo local del backend.
* **Node.js (v20 o superior)** y **npm**: Para desarrollo local del frontend.
* **Docker y Docker Compose**: Para levantar el entorno completo en contenedores.

---

## 💻 Desarrollo Local (Modo Rápido / Hot-Reload)

### 1. Iniciar el Backend

```bash
cd backend

# En Windows:
.\mvnw.cmd spring-boot:run

# En Linux / macOS:
./mvnw spring-boot:run
```
El backend estará disponible en `http://localhost:8080`.
*   **Estado del sistema**: `http://localhost:8080/api/status` o `http://localhost:8080/`
*   **Swagger UI (OpenAPI 3)**: `http://localhost:8080/swagger-ui.html` o `http://localhost:8080/swagger-ui/index.html`
*   **Especificación OpenAPI JSON**: `http://localhost:8080/api-docs`
*   **Consola H2 (Base en memoria)**: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:template_db`, User: `sa`, Password: en blanco)

### 2. Iniciar el Frontend

```bash
cd frontend
npm install
npm start
```
El frontend estará corriendo en `http://localhost:4200` con recarga en vivo (HMR).

---

## 🐳 Ejecución con Docker Compose (Stack Completo)

Para levantar toda la solución en contenedores aislados con un solo comando:

```bash
docker compose up --build
```

* **Frontend**: `http://localhost` (o `http://localhost:4200`)
* **Backend**: `http://localhost:8080`

Para detener los contenedores:
```bash
docker compose down
```

---

## 🔧 ¿Cómo Extender el Template?

### 1. Agregar una Base de Datos (ej. PostgreSQL)

1. En `docker-compose.yml`, descomentá el servicio `database` y el volumen `pgdata`.
2. En `backend/pom.xml`, agregá las dependencias de JPA y PostgreSQL:
   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-data-jpa</artifactId>
   </dependency>
   <dependency>
       <groupId>org.postgresql</groupId>
       <artifactId>postgresql</artifactId>
       <scope>runtime</scope>
   </dependency>
   ```
3. En `backend/src/main/resources/application.yml`, configurá la conexión usando variables de entorno:
   ```yaml
   spring:
     datasource:
       url: ${SPRING_DATASOURCE_URL:jdbc:postgresql://localhost:5432/template_db}
       username: ${SPRING_DATASOURCE_USERNAME:postgres}
       password: ${SPRING_DATASOURCE_PASSWORD:postgres}
     jpa:
       hibernate:
         ddl-auto: update
   ```

### 2. Agregar Seguridad (Spring Security)

Cuando el proyecto requiera autenticación:
1. Agregá `spring-boot-starter-security` en `backend/pom.xml`.
2. Creá una clase de configuración `SecurityFilterChain` en `backend/src/main/java/com/example/template/config/SecurityConfig.java`.

---

## 🧪 Pruebas Unitarias del Backend

Las pruebas automatizadas se centran en el Backend para garantizar la integridad transaccional y la lógica del negocio:

```bash
# Ejecutar tests del backend y generar reporte JaCoCo
cd backend

# En Windows:
.\mvnw.cmd test

# En Linux / macOS:
./mvnw test
```
*   El reporte de cobertura se genera en `backend/target/site/jacoco/index.html`.
*   El frontend prioriza desarrollo visual ágil con Angular Signals y Tailwind sin sobrecarga de tests unitarios de UI.
