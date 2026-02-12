# Task Manager (Spring Boot)

A lightweight **Spring Boot task management application** demonstrating user authentication and basic CRUD operations for tasks using an in‑memory H2 database. This project was built to showcase clean Spring Boot architecture, security configuration, and MVC‑based web development.

---

## Overview

The Task Manager application allows authenticated users to log in and manage their personal tasks through a simple web interface. It uses **Spring Security** for authentication, **Spring Data JPA** for persistence, and **Thymeleaf** for server‑side rendering.

The project is ideal for understanding how authentication, database access, and MVC patterns work together in a Spring Boot application.

---

## Features

* ✅ User authentication using Spring Security
* 🔐 Password encryption with BCrypt
* 📝 Create, read, update, and delete (CRUD) tasks
* 🗄️ In‑memory H2 database for fast setup
* 🧩 Spring Data JPA for database interactions
* 🎨 Thymeleaf templates for UI (login, task list, edit task)
* 🛠️ H2 Console enabled for debugging

---

## Tech Stack

**Backend:**

* Spring Boot 3.x
* Spring Security
* Spring Data JPA

**Database:**

* H2 (in‑memory)

**Frontend (Server‑Side Rendering):**

* Thymeleaf
* HTML / CSS

**Build Tool:**

* Maven

**Java Version:**

* Java 17 (recommended)

---

## Application Flow

1. User accesses the login page
2. Spring Security handles authentication
3. Authenticated users can view and manage tasks
4. Task data is stored in the H2 database
5. Thymeleaf renders dynamic views for user interaction

---

## Endpoints

* 🔑 Login Page: `http://localhost:8080/login`
* 📋 Task List (Authenticated): `http://localhost:8080/tasks`
* 🗄️ H2 Console: `http://localhost:8080/h2-console`

> Note: The application uses **form‑based login**. Users can be seeded directly in the H2 database or extended with a registration flow.

---

## Security Notes

* Authentication is handled via **Spring Security** with a `DaoAuthenticationProvider`
* Passwords are stored using **BCrypt hashing**
* Frame options are disabled only for `/h2-console/**` to allow the H2 console to render

⚠️ **Production Recommendation:**

* Use `frameOptions().sameOrigin()` instead of disabling frame options
* Disable the H2 console
* Replace H2 with a persistent database (MySQL / PostgreSQL)

---

## Prerequisites

* Java 17 installed and configured (`JAVA_HOME` set)
* Maven 3.8+ installed

---

## Quick Start (Windows PowerShell)

### Build the project (skip tests)

```powershell
mvn -f "${PWD}\pom.xml" clean package -DskipTests
```

### Run using Maven

```powershell
mvn -f "${PWD}\pom.xml" spring-boot:run
```

### Run the packaged JAR

```powershell
java -jar target\task-manager-springboot-0.0.1-SNAPSHOT.jar
```

---

## Troubleshooting

### Circular Bean Creation Error

If you encounter:

```
BeanCurrentlyInCreationException
```

**Cause:** Circular dependency in security configuration.

**Solution:**

* Register `DaoAuthenticationProvider` as a separate bean
* Inject it into the `SecurityFilterChain`
* Avoid calling encoder factory methods inside the same `@Configuration` class

---

## Development Tips

* Prefer **constructor injection** over field `@Autowired`
* Keep security configuration modular
* Use profiles for dev vs production settings

---

## Useful Commands

```powershell
# Build
mvn clean package -DskipTests

# Run
mvn spring-boot:run

# Smoke test
Invoke-WebRequest -Uri http://localhost:8080/login -Method GET
```

---

## Learning Outcomes

* Spring Boot MVC architecture
* Secure authentication with Spring Security
* JPA entity and repository design
* Handling common Spring security pitfalls
* Building production‑ready configurations

---

## Contributing

Small improvements and fixes are welcome. For major changes or refactors, please open an issue first to discuss the design.

---

## License

No license is currently specified. Add a `LICENSE` file (MIT / Apache‑2.0 recommended) if you plan to publish or reuse this project.

---

