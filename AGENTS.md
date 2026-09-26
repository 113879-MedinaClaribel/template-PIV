# AGENTS.md — Repository Instructions for AI Pair Programmers

Welcome to the **Repo-Base** project. You are an expert Software Architect pair-programming with the lead engineer.
All AI agents, coding assistants, and subagents operating within this workspace MUST strictly adhere to the following rules and standards without exception.

---

## 1. Golden Rules (Non-Negotiable)

1. **LANGUAGE IS STRICTLY ENGLISH**:
   - Every line of code, class name, interface, method, variable, DTO, entity, commit message, code comment, and technical doc MUST be in English.
2. **NO REMOTE GIT PUSHES (GITHUB SAFEGUARD)**:
   - **NEVER** run `git push` or publish commits/branches to GitHub or any remote repository under any circumstances. All Git operations must remain strictly local.
3. **90% BACKEND TEST COVERAGE TARGET (WARNING AUDIT)**:
   - Backend features, business services, and controllers must aim for at least **90% test coverage**.
   - Backend builds audit this via `jacoco-maven-plugin` (`<haltOnFailure>false</haltOnFailure>`), emitting explicit warnings if the threshold is not reached without abruptly breaking development builds. Unit tests (JUnit 5 + Mockito) and Slice tests (`@WebMvcTest`) remain standard practice.
   - Frontend tests are **NOT required** — frontend development prioritizes rapid UI iteration, fine-grained Signals reactivity, and visual verification.
4. **DATA PERSISTENCE INTEGRITY**:
   - Never assume data persists automatically or mock persistence blindly.
   - Always enforce transactional boundaries (`@Transactional`), schema constraints, audit fields, and data validation (Jakarta Bean Validation `@NotNull`, `@Size`, etc.).
   - Verify persistence contracts with integration tests using test slices or containerized databases.
5. **SCALABLE & HIGH-PERFORMANCE ARCHITECTURE**:
   - **Backend**: Leverage Java 21 Virtual Threads (`spring.threads.virtual.enabled: true`), stateless services, non-blocking I/O, immutable DTOs (Records), and connection pooling.
   - **Frontend**: Zero NgModules. Standalone components only. Use fine-grained reactivity with **Angular Signals** (`signal()`, `computed()`), `ChangeDetectionStrategy.OnPush`, lazy-loaded routes, and Tailwind CSS.
6. **DESIGN PATTERNS IN IMPLEMENTATION PLANS**:
   - When designing or planning a new module, you MUST identify and propose appropriate design patterns:
     - *Strategy Pattern*: When multiple business algorithms or behaviors vary dynamically.
     - *Factory / Builder Pattern*: For assembling complex domain models or DTOs.
     - *Adapter Pattern*: For external third-party integrations (payments, mailers, persistence).
     - *Container-Presentational (Smart/Dumb)*: For UI separation in Angular.

---

## 2. Backend Guidelines (Spring Boot 3.4.x + Java 21)

* **Build Tool**: Use the Maven Wrapper (`./mvnw` or `.\mvnw.cmd`). Do not rely on global Maven.
* **Architecture Layers**:
  - `controllers/`: HTTP boundary only. Validates requests and returns `ResponseEntity<DTO>`. No business logic.
  - `services/`: Interfaces (`XService`) and implementations (`XServiceImpl`). Stateless, annotated with `@Service`.
  - `dtos/`: Immutable Java 21 `record`. **NEVER** expose database entities (`@Entity`) directly to REST controllers or clients.
  - `exceptions/`: Domain exceptions extending `BusinessException`. Handled uniformly via `GlobalExceptionHandler` (`@RestControllerAdvice`) returning `ErrorResponseDTO`.
  - `config/`: Security, CORS, OpenAPI, and application beans.
* **Dependency Injection**: Use constructor-based injection exclusively. Mark fields as `private final`. Do NOT use `@Autowired` on fields.

---

## 3. Frontend Guidelines (Angular 20 + Signals + Tailwind)

* **Standalone Architecture**: All components, directives, and pipes must be standalone.
* **Reactivity**: Prefer Angular **Signals** (`signal()`, `computed()`, `effect()`) for component state management over raw RxJS subscriptions. Use RxJS primarily for HTTP streaming with proper cleanup or `toSignal()`.
* **Injection**: Use the functional `inject()` function instead of constructor parameter injection.
* **Styling**: Use utility-first classes with **Tailwind CSS**. Do not write custom ad-hoc CSS unless strictly necessary.
* **Directory Structure**:
  - `core/`: Singleton services, HTTP interceptors, global models.
  - `shared/`: Reusable, stateless UI presentational components (buttons, modals, tables).
  - `features/<feature-name>/`: Domain modules containing smart container components and feature routes.

---

## 4. Git & Commit Guidelines

* Use **Conventional Commits** format only:
  - `feat: add user authentication service`
  - `fix: correct validation constraint in OrderRequestDTO`
  - `refactor: extract payment strategy to separate adapter`
  - `test: add unit tests for StatusService achieving 95% coverage`
* **NEVER** append "Co-Authored-By" or AI attribution tags to commit messages.
* **NEVER** execute `git push` to origin or upstream.

---

## 5. Available Workspace Skills

When scaffolding or designing new features, refer to the procedural skill in `.agent/skills/feature-scaffold/SKILL.md`.
