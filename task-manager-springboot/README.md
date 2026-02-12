# Task Manager (Spring Boot)

A small Spring Boot application for managing tasks with simple authentication and an in-memory H2 database. This project was used to demonstrate a basic tasks app with user registration/login and simple CRUD for tasks.

## Features

- Spring Boot 3.x
- Spring Security for authentication
- Spring Data JPA with H2 (in-memory) database
- Thymeleaf templates for views (login, tasks, edit)
- H2 console available for debugging

## Prerequisites

- Java 17 (recommended) or later installed
- Maven 3.8+ installed

> Note: The project was compiled targeting Java 17 in the build configuration. Running with newer JDKs may work but using Java 17 ensures compatibility with the compiler settings shown in the project.

## Quick start (Windows PowerShell)

1. Build the project (skip tests):

```powershell
mvn -f "${PWD}\pom.xml" clean package -DskipTests
```

2. Run the app with Maven (development):

```powershell
mvn -f "${PWD}\pom.xml" spring-boot:run
```

3. Or run the packaged jar:

```powershell
java -jar target\task-manager-springboot-0.0.1-SNAPSHOT.jar
```

## Endpoints

- Web UI (login): http://localhost:8080/login
- Tasks list (requires auth): http://localhost:8080/tasks
- H2 console (useful for debugging): http://localhost:8080/h2-console

The app uses form login; register or seed users in the H2 database (or implement a registration flow). Passwords are stored using BCrypt.

## Security notes

- The project config (`src/main/java/com/example/taskmanagerspringboot/config/SecurityConfig.java`) currently disables frame options via a configuration to allow the H2 console to render: the H2 console path (`/h2-console/**`) is permitted and X-Frame-Options is disabled for that route.

  - For production, prefer limiting frame options to `sameOrigin()` instead of disabling them entirely, and only enable the H2 console for non-production profiles.

- The authentication wiring uses a `DaoAuthenticationProvider` registered as a bean and passed into the `SecurityFilterChain` to avoid circular bean creation problems.

## Troubleshooting

- Circular bean creation on startup (example error):

```
org.springframework.beans.factory.BeanCurrentlyInCreationException: Error creating bean with name 'securityConfig': Requested bean is currently in creation: Is there an unresolvable circular reference?
```

  - This project previously configured authentication by calling the password encoder factory method from within an `@Autowired` setup method inside the same `@Configuration` class which caused a circular reference. The fix was to expose a `DaoAuthenticationProvider` bean and register it on the `SecurityFilterChain` (no back-call into the configuration method).

- If the application doesn't start, confirm:
  - You have the right JDK installed and JAVA_HOME configured.
  - Port 8080 is not already in use.
  - Check logs printed by `mvn spring-boot:run` for stack traces.

## Development tips

- Prefer constructor injection instead of field `@Autowired` for beans; it's easier to test and avoids some lifecycle issues.
- To harden security for production:
  - Do not disable frame options; use `frameOptions().sameOrigin()` if the UI needs to render frames.
  - Disable the H2 console and use a persistent database.

## Useful commands

PowerShell examples:

```powershell
# Build
mvn -f "${PWD}\pom.xml" clean package -DskipTests

# Run with Maven
mvn -f "${PWD}\pom.xml" spring-boot:run

# Run the built jar
java -jar target\task-manager-springboot-0.0.1-SNAPSHOT.jar

# Quick HTTP smoke-test (PowerShell)
Invoke-WebRequest -Uri http://localhost:8080/login -UseBasicParsing -Method GET
```

## Contributing

Small fixes and improvements are welcome. Please follow the existing code style. For larger refactors (breaking cycles, extracting services), open an issue first to discuss design.

## License

This project doesn't include an explicit license file. If you plan to publish it, add a LICENSE file (for example MIT or Apache-2.0) to clarify reuse terms.
