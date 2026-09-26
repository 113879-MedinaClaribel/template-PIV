---
name: feature-scaffold
description: Scaffolds a new end-to-end fullstack feature across Spring Boot 3 (Java 21) and Angular 20 adhering to Clean Architecture, 90% test coverage, and design patterns.
---

# Feature Scaffold Skill (`feature-scaffold`)

Use this skill whenever you need to add a new business module, CRUD feature, or integration into the project.

---

## Pre-Requisites & Project Constraints

1. **Language**: All code, identifiers, tests, comments, and commit messages MUST be in English.
2. **Coverage**: Backend code MUST aim for **90% test coverage** audited via JaCoCo (Frontend tests are not required).
3. **No Push**: Never run `git push` to GitHub or any remote repository.
4. **Persistence Integrity**: Always corroborate data persistence contracts, validation, and transaction boundaries.

---

## Step-by-Step Scaffolding Procedure

### Step 1: Architectural Design & Pattern Identification
Before writing code, identify the design patterns suited for the domain problem:
- **Strategy Pattern**: If the feature requires interchangeable algorithms (e.g., payment methods, fee calculators).
- **Factory / Builder Pattern**: For assembling complex aggregate roots or DTO mappings.
- **Adapter Pattern**: For isolating external APIs, messaging, or persistence layers.
- **Container-Presentational**: In Angular, separate smart components (state/services) from presentational dumb components (pure inputs/outputs).

---

### Step 2: Backend DTOs & Validation (`backend/src/main/java/.../dtos/`)
Create immutable Java 21 Records for requests and responses. Apply Jakarta Bean Validation constraints.

```java
package com.example.template.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateProductRequestDTO(
    @NotBlank(message = "Product name must not be blank")
    String name,

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be strictly positive")
    Double price
) {}
```

```java
package com.example.template.dtos;

import java.time.Instant;

public record ProductResponseDTO(
    Long id,
    String name,
    Double price,
    Instant createdAt
) {}
```

---

### Step 3: Backend Service Layer (`services/` and `services/impl/`)
Define the service interface first, then the implementation with constructor injection and `@Transactional`.

* Convention: `<Feature>Service.java` & `<Feature>ServiceImpl.java`
* Requirements:
  - Inject required dependencies via constructor (mark `private final`).
  - Encapsulate business validation and throw `BusinessException` for rule breaches.
  - Verify persistence operations explicitly.

---

### Step 4: Backend REST Controller (`controllers/`)
Expose standard RESTful endpoints.
* Anotate with `@RestController` and `@RequestMapping("/api/<feature-plural>")`.
* Always validate incoming payloads with `@Valid`.
* Return `ResponseEntity<DTO>`.

---

### Step 5: Backend Test Suite (90%+ Coverage Target)
Write JUnit 5 unit tests for the service and MockMvc tests for the controller.

* Ensure branch and line coverage exceed 90%.
* Verify edge cases: invalid payloads, entity not found, duplicate keys.
* Run tests locally via `./mvnw test` to ensure JaCoCo rule passes.

---

### Step 6: Frontend Model & API Service (`frontend/src/app/core/`)
* Create TypeScript model: `core/models/<feature>.model.ts`
* Create HTTP Service: `core/services/<feature>.service.ts` using `inject(HttpClient)` and typed Observables.

---

### Step 7: Frontend Feature Component (`frontend/src/app/features/<feature>/`)
* Create standalone component: `features/<feature>/<feature>.component.ts`
* Manage state reactively using Angular **Signals** (`signal()`, `computed()`).
* Style exclusively with **Tailwind CSS**.
* Implement container-presentational pattern when UI exceeds simple rendering.

---

### Step 8: Git Commit
Create a conventional commit locally:
```bash
git add .
git commit -m "feat(<feature>): implement fullstack feature with 90% test coverage"
```
**CRITICAL**: Do NOT run `git push`.
