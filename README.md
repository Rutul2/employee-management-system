# Employee Management System

A Spring Boot REST API demonstrating layered backend design, relational persistence, validation, role-based access control, pagination, API documentation, and automated testing.

## Stack
Java 17, Spring Boot 3.4, Spring Web, Spring Data JPA, Spring Security, MySQL/H2, JUnit 5, Mockito, Maven, Swagger/OpenAPI.

## Run locally
1. Install JDK 17+ and Maven 3.9+.
2. In this directory run `mvn spring-boot:run`.
3. The default database is an in-memory H2 database. Data resets when the application stops.
4. Swagger UI: `http://localhost:8080/swagger-ui.html`.
5. H2 console: `http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:mem:appdb`, username `sa`, blank password).

## Demo accounts
- Admin: `admin` / `Admin123!` (can create, update, and delete)
- Staff: `staff` / `Staff123!` (read-only access)

These credentials are for local demonstration only. Replace in-memory users and configure secrets securely before any deployment.

## Use MySQL
Create a database or let MySQL create it through the JDBC URL, then set environment variables `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD`, and run with profile `mysql`: `mvn spring-boot:run -Dspring-boot.run.profiles=mysql`.

## Example requests
```bash
curl -u admin:Admin123! http://localhost:8080/api/departments
curl -u admin:Admin123! -H 'Content-Type: application/json' -d '{"name":"Engineering","description":"Software team"}' http://localhost:8080/api/departments
curl -u admin:Admin123! -H 'Content-Type: application/json' -d '{"name":"Asha Patel","email":"asha@example.com","salary":45000,"departmentId":1}' http://localhost:8080/api/employees
curl -u staff:Staff123! 'http://localhost:8080/api/employees?page=0&size=10&sortBy=name'
```

## Test
Run `mvn test`. The test suite includes an application context smoke test; add controller/service tests as you extend the project.
