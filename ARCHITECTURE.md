# System Architecture & Technical Specifications

## 1. Overview & Architectural Principles

This repository provides an enterprise-ready template structured under **Clean Architecture**, **Screaming Architecture**, and **Component-Driven Design**.

```text
       ┌────────────────────────┐
       │   Angular 20 SPA       │
       │   (Signals + Tailwind) │
       └───────────┬────────────┘
                   │ HTTP / JSON (Fetch API)
                   ▼
       ┌────────────────────────┐
       │   Spring Boot 3.4.x    │
       │   (Java 21 + Loom)     │
       └───────────┬────────────┘
                   │ Persistence Adapter (JPA / Mongo / In-Memory)
                   ▼
       ┌────────────────────────┐
       │     Database Engine    │
       └────────────────────────┘
```

### Core Design Principles:
1. **Separation of Concerns**: Business domain logic does not depend on UI or persistence frameworks.
2. **Immutability & Thread Safety**: All DTOs are Java 21 `record` types. Concurrency leverages Virtual Threads (Project Loom) with zero blocking overhead.
3. **Reactive UI State**: Angular Signals provide fine-grained reactivity and minimal change detection cycles.
4. **Resilient Containerization**: Multi-stage Docker builds ensure lightweight, secure, and production-ready images.

---

## 2. Design Patterns Catalog

Any new feature designed for this repository must evaluate and apply suitable design patterns:

| Pattern | Layer | Purpose |
| :--- | :--- | :--- |
| **Repository Pattern** | Backend (Persistence) | Decouples business logic from persistence implementation details. |
| **Strategy Pattern** | Backend (Domain) | Encapsulates interchangeable business algorithms or calculation rules. |
| **Factory / Builder** | Backend (Domain/DTO) | Safely constructs complex domain aggregates and handles mapping. |
| **Adapter Pattern** | Backend (Infrastructure)| Wraps external services (payment gateways, notification providers). |
| **Observer (Signals)** | Frontend (State) | Reactively propagates state updates to the UI without memory leaks. |
| **Container / Presentational** | Frontend (Components)| Isolates stateful data-fetching components from pure UI components. |

---

## 3. Quality & Testing Strategy (Backend 90% Target)

* **Backend Quality Audit**: Monitored via the `jacoco-maven-plugin` targeting **0.90 (90%)** line coverage, logging build warnings on violations without breaking development flow.
* **Backend Testing Pyramid**:
  - **Unit Tests**: Test domain services and business validators in isolation using JUnit 5 and Mockito.
  - **Slice Tests**: Test controllers using `@WebMvcTest` to verify HTTP status codes, validation, and serialization.
  - **Integration Tests**: Verify database transactions and persistence adapters.
* **Frontend Testing Policy**: Automated tests on the frontend are intentionally omitted to maximize UI iteration speed and visual design agility with Angular Signals and Tailwind CSS.

---

## 4. Git Workflow & Safety

* **Language**: All code and commits in English.
* **Format**: Conventional Commits (`feat:`, `fix:`, `refactor:`, `test:`).
* **Remote Safeguard**: Remote pushes (`git push`) are strictly prohibited in automated tasks.
