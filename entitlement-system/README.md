# Centralized Entitlement System

A Spring Boot 3.x application for managing entitlements, roles, and auditing across multiple applications. Uses Hibernate Envers for revision-based auditing and supports both H2 (local) and Oracle DB.

## Features
- Centralized entitlement and role management
- Per-user and per-role access control
- Full revision-based auditing (_AUD tables)
- REST API for onboarding, assignment, and audit history
- Basic Auth security (in-memory, can be replaced with BAM/SSO)
- Sample data for H2

## Tech Stack
- Java 17+
- Spring Boot 3.x
- Spring Data JPA
- Hibernate Envers
- H2 (local) and Oracle (prod)
- Maven

## Getting Started

### Prerequisites
- Java 17+
- Maven

### Run with H2 (default)
```bash
cd entitlement-system
mvn spring-boot:run
```
- Access H2 console at: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:entitlementdb`)
- API base: `http://localhost:8080/api/`
- Auth: Basic Auth (user: `admin`, pass: `adminpass`)

### Run with Oracle
1. Update `src/main/resources/application.yml` with your Oracle DB credentials.
2. Use `--spring.profiles.active=oracle`:
```bash
mvn spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=oracle
```

### Oracle DDL
See `oracle-ddl.sql` for all table and audit table definitions, including Envers revision tables.

## Environments

This application supports multiple environments using Spring profiles:
- `dev`: Development
- `uat`: User Acceptance Testing
- `demo`: Demo
- `prod`: Production

Each environment has its own configuration file in `src/main/resources/` (e.g., `application-dev.yml`).

**To run with a specific profile:**
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```
Or set the environment variable:
```bash
export SPRING_PROFILES_ACTIVE=dev
```

## API Documentation (Swagger/OpenAPI)

Swagger UI is enabled by default. After starting the application, access:
- [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) (or the port for your environment)
- OpenAPI JSON: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## REST Endpoints
- `/api/roles` - CRUD for roles
- `/api/roles/{id}/audit` - Role audit history
- `/api/roles/onboard` - Onboard user to app
- `/api/user-entitlements` - CRUD for user entitlements
- `/api/user-entitlements/{id}/audit` - User entitlement audit history
- `/api/user-entitlements/assign` - Assign entitlement to user
- `/api/groups` - CRUD for groups
- `/api/groups/{id}/audit` - Group audit history
- `/api/group-maps` - CRUD for group maps
- `/api/group-maps/{id}/audit` - Group map audit history
- `/api/role-xrefs` - CRUD for role xrefs
- `/api/role-xrefs/{id}/audit` - Role xref audit history
- `/api/user-ent-xref-hist` - CRUD for user entitlement xref history

## Test Data
Sample data is loaded automatically for H2 from `src/main/resources/data.sql`.

## Security
- Basic Auth (user: `admin`, pass: `adminpass`)
- Placeholder for BAM/SSO integration in `SecurityConfig`

## License
MIT 